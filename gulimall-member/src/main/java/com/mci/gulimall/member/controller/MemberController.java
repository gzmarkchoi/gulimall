package com.mci.gulimall.member.controller;

import java.util.Arrays;
import java.util.Map;

import com.mci.common.exception.BizCodeEnume;
import com.mci.gulimall.member.exception.PhoneExistsException;
import com.mci.gulimall.member.exception.UsernameExistsException;
import com.mci.gulimall.member.feign.CouponFeignService;
import com.mci.gulimall.member.vo.MemberLoginVo;
import com.mci.gulimall.member.vo.MemberRegisterVo;
import com.mci.gulimall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mci.gulimall.member.entity.MemberEntity;
import com.mci.gulimall.member.service.MemberService;
import com.mci.common.utils.PageUtils;
import com.mci.common.utils.R;


/**
 * 会员
 *
 * @author gzmarkchoi
 * @email @gmail.com
 * @date 2021-01-06 18:07:23
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponFeignService couponFeignService;

    /**
     * Call the "coupon" service
     *
     * @return
     */
    @RequestMapping("/coupons")
    public R test() {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setNickname("Ma Ch");

        R memberCoupons = couponFeignService.memberCoupons();

        return R.ok().put("member", memberEntity).put("coupons", memberCoupons.get("coupons"));
    }

    @PostMapping("/oauth2/login")
    public R oauthLogin(@RequestBody SocialUser socialUser) {

        MemberEntity entity = memberService.login(socialUser);
        if (entity != null) {
            return R.ok();
        } else {
            return R.error(BizCodeEnume.LOGIN_ACCOUNT_PASSWORD_INVALID_EXCEPTION.getCode(),
                    BizCodeEnume.LOGIN_ACCOUNT_PASSWORD_INVALID_EXCEPTION.getMsg());
        }
    }

    @PostMapping("/login")
    public R login(@RequestBody MemberLoginVo vo) {

        MemberEntity entity = memberService.login(vo);
        if (entity != null) {
            return R.ok();
        } else {
            return R.error(BizCodeEnume.LOGIN_ACCOUNT_PASSWORD_INVALID_EXCEPTION.getCode(),
                    BizCodeEnume.LOGIN_ACCOUNT_PASSWORD_INVALID_EXCEPTION.getMsg());
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody MemberRegisterVo vo) {
        try {
            memberService.register(vo);
        } catch (PhoneExistsException e) {
            return R.error(BizCodeEnume.PHONE_ALREADY_EXIST_EXCEPTION.getCode(),
                    BizCodeEnume.PHONE_ALREADY_EXIST_EXCEPTION.getMsg());
        } catch (UsernameExistsException e) {
            return R.error(BizCodeEnume.USER_ALREADY_EXIST_EXCEPTION.getCode(),
                    BizCodeEnume.USER_ALREADY_EXIST_EXCEPTION.getMsg());
        }

        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
