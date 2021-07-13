package com.mci.gulimall.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mci.common.utils.HttpUtils;
import com.mci.gulimall.member.dao.MemberLevelDao;
import com.mci.gulimall.member.entity.MemberLevelEntity;
import com.mci.gulimall.member.exception.PhoneExistsException;
import com.mci.gulimall.member.exception.UsernameExistsException;
import com.mci.gulimall.member.vo.MemberLoginVo;
import com.mci.gulimall.member.vo.MemberRegisterVo;
import com.mci.gulimall.member.vo.SocialUser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.Query;

import com.mci.gulimall.member.dao.MemberDao;
import com.mci.gulimall.member.entity.MemberEntity;
import com.mci.gulimall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberRegisterVo vo) {
        MemberDao memberDao = this.baseMapper;
        MemberEntity memberEntity = new MemberEntity();

        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(levelEntity.getId());

        memberEntity.setMobile(vo.getPhone());
        memberEntity.setUsername(vo.getUserName());

        // Double check user name and phone unity in DB
        checkPhoneIsUnique(vo.getPhone());
        checkUserNameIsUnique(vo.getUserName());

        // password encryption
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        memberEntity.setPassword(encode);

        // other information

        memberDao.insert(memberEntity);
    }

    @Override
    public void checkPhoneIsUnique(String phone) throws PhoneExistsException {
        MemberDao memberDao = this.baseMapper;
        Integer mobileCount = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));

        if (mobileCount > 0) {
            throw new PhoneExistsException();
        }
    }


    @Override
    public void checkUserNameIsUnique(String userName) throws UsernameExistsException {
        MemberDao memberDao = this.baseMapper;
        Integer userNameCount = memberDao.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));

        if (userNameCount > 0) {
            throw new UsernameExistsException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String loginAccount = vo.getLoginAccount();
        String password = vo.getPassword();

        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("username", loginAccount)
                .or().eq("mobile", loginAccount));
        if (entity == null) {
            // login fail
            return null;
        } else {
            String passwordDb = entity.getPassword();

            // password verification with encoder
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean matches = passwordEncoder.matches(password, passwordDb);

            if (matches) {
                // login success
                return entity;
            } else {
                return null;
            }
        }
    }

    @Override
    public MemberEntity login(SocialUser socialUser) throws Exception {
        // Login and Register
        String uid = socialUser.getUid();

        // 1. check if user already registered or not
        MemberDao memberDao = this.baseMapper;

        MemberEntity memberEntity = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", uid));
        if (memberEntity != null) {
            // user already registered
            MemberEntity memberUpdated = new MemberEntity();
            memberUpdated.setId(memberEntity.getId());
            memberUpdated.setAccessToken(memberEntity.getAccessToken());
            memberUpdated.setExpiresIn(memberEntity.getExpiresIn());

            memberDao.updateById(memberUpdated);

            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());

            return memberEntity;
        } else {
            // new user
            MemberEntity newUser = new MemberEntity();

            try {
                // get user info
                Map<String, String> query = new HashMap<>();
                query.put("access_token", socialUser.getAccess_token());
                query.put("uid", socialUser.getUid());

                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<String, String>(), query);
                if (response.getStatusLine().getStatusCode() == 200) {
                    // success
                    String json = EntityUtils.toString(response.getEntity());
                    JSONObject jsonObject = JSON.parseObject(json);
                    String name = jsonObject.getString("name");
                    String gender = jsonObject.getString("gender");

                    newUser.setNickname(name);
                    newUser.setGender("m".equals(gender) ? 1 : 0);
                }
            } catch (Exception e) {

            }

            newUser.setSocialUid(socialUser.getUid());
            newUser.setAccessToken(socialUser.getAccess_token());
            newUser.setExpiresIn(socialUser.getExpires_in());

            memberDao.insert(newUser);

            return newUser;
        }
    }

}