package com.mci.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mci.common.utils.PageUtils;
import com.mci.gulimall.member.entity.MemberEntity;
import com.mci.gulimall.member.exception.PhoneExistsException;
import com.mci.gulimall.member.exception.UsernameExistsException;
import com.mci.gulimall.member.vo.MemberLoginVo;
import com.mci.gulimall.member.vo.MemberRegisterVo;

import java.util.Map;

/**
 * 会员
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 18:07:23
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(MemberRegisterVo vo);

    void checkPhoneIsUnique(String phone) throws PhoneExistsException;

    void checkUserNameIsUnique(String userName) throws UsernameExistsException;

    MemberEntity login(MemberLoginVo vo);
}

