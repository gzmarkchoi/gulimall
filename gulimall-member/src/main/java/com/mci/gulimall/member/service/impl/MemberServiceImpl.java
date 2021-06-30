package com.mci.gulimall.member.service.impl;

import com.mci.gulimall.member.dao.MemberLevelDao;
import com.mci.gulimall.member.entity.MemberLevelEntity;
import com.mci.gulimall.member.exception.PhoneExistsException;
import com.mci.gulimall.member.exception.UsernameExistsException;
import com.mci.gulimall.member.vo.MemberRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        // TODO password encryption
        memberEntity.setPassword(vo.getPassword());


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

}