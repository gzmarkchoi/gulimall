package com.mci.gulimall.auth.controller;

import com.mci.common.constant.AuthServerConstant;
import com.mci.common.exception.BizCodeEnume;
import com.mci.common.utils.R;
import com.mci.gulimall.auth.feign.ThirdPartyFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {

    @Autowired
    ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @ResponseBody
    @GetMapping("/sms/sendcode")
    public R sendCode(@RequestParam("phone") String phone) {

        String redisCode = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        long lastTimeMillis = Long.parseLong(redisCode.split("_")[1]);
        if (!StringUtils.isEmpty(redisCode)) {
            // same phone number already used before
            if (System.currentTimeMillis() - lastTimeMillis < 60000) {
                // Could not send another code under 60 seconds
                return R.error(BizCodeEnume.SMS_CODE_EXCEPTION.getCode(), BizCodeEnume.SMS_CODE_EXCEPTION.getMsg());
            }

        }

        String code = UUID.randomUUID().toString().substring(0, 5) + "_" + System.currentTimeMillis();
        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, code, 10, TimeUnit.MINUTES);

        thirdPartyFeignService.sendCode(phone, code);

        return R.ok();
    }
}
