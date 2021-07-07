package com.mci.gulimall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.mci.common.constant.AuthServerConstant;
import com.mci.common.exception.BizCodeEnume;
import com.mci.common.utils.R;
import com.mci.gulimall.auth.feign.MemberFeignService;
import com.mci.gulimall.auth.feign.ThirdPartyFeignService;
import com.mci.gulimall.auth.vo.UserLoginVo;
import com.mci.gulimall.auth.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class LoginController {

    @Autowired
    ThirdPartyFeignService thirdPartyFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MemberFeignService memberFeignService;

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

        String code = UUID.randomUUID().toString().substring(0, 5);
        String codeRedis = code + "_" + System.currentTimeMillis();

        redisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, codeRedis, 10, TimeUnit.MINUTES);

        thirdPartyFeignService.sendCode(phone, code);

        return R.ok();
    }

    @PostMapping("register")
    public String register(@Valid UserRegisterVo vo, BindingResult result, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (result.hasErrors()) {
            /**
             * .map(fieldError -> {
             *                 String field = fieldError.getField();
             *                 String defaultMessage = fieldError.getDefaultMessage();
             *                 errors.put(field, defaultMessage);
             *             })
             */
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            redirectAttributes.addFlashAttribute("errors", errors);

            // TODO session management in distributed systems
            // httpSession.

            return "redirect:http://auth.gulimall.com/register.html";
        }

        // Register via Feign service
        String code = vo.getCode();

        // get QR code in Redis if it exists
        String codeInRedis = redisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (!StringUtils.isEmpty(codeInRedis)) {
            if (code.equals(codeInRedis.split("_")[0])) {
                // delete QR code in Redis
                redisTemplate.delete(AuthServerConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());

                // QR code OK, go register
                R r = memberFeignService.register(vo);
                if (r.getCode() == 0) { // success
                    return "redirect:http://auth.gulimall.com/login.html";
                } else {
                    Map<String, String> errors = new HashMap<>();
                    errors.put("msg", r.getData("msg", new TypeReference<String>() {
                    }));
                    redirectAttributes.addFlashAttribute("errors", errors);

                    return "redirect:http://auth.gulimall.com/register.html";
                }
            } else {
                Map<String, String> errors = new HashMap<>();
                errors.put("code", "QR code error");
                redirectAttributes.addFlashAttribute("errors", errors);

                return "redirect:http://auth.gulimall.com/register.html";
            }
        } else {
            // QR code verification error
            Map<String, String> errors = new HashMap<>();
            errors.put("code", "QR code error");
            redirectAttributes.addFlashAttribute("errors", errors);

            return "redirect:http://auth.gulimall.com/register.html";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes) {

        R r = memberFeignService.login(vo);
        if (r.getCode() == 0) {
            // success then go to gulimall home page
            return "redirect:http://gulimall.com";
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", r.getData("msg", new TypeReference<String>() {
            }));
            redirectAttributes.addFlashAttribute("errors", errors);

            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
