package com.mci.gulimall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mci.common.utils.HttpUtils;
import com.mci.common.utils.R;
import com.mci.gulimall.auth.feign.MemberFeignService;
import com.mci.common.vo.MemberResponseVo;
import com.mci.gulimall.auth.vo.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Login using Social media
 */
@Slf4j
@Controller
public class OAuth2Controller {
    @Autowired
    MemberFeignService memberFeignService;

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code, HttpSession session) throws Exception {
        // 1. use code to get access token
        Map<String, String> map = new HashMap<>();
        map.put("client_id", "343223535");
        map.put("client_secret", "fdjfoiuer4545qjsfdfs");
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", "http://gulimall.com/oauth2.0/weibo/success");
        map.put("code", "42342366868");

        HttpResponse response = HttpUtils.doPost("api.weibo.com", "/oauth2/access_token", "post",
                map, null, map);

        // 2. go back to home page if login successful
        if (response.getStatusLine().getStatusCode() == 200) {
            // get access token
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);

            // get social user
            // 2.1. if first time login, register user with the social user info
            R oauthLogin = memberFeignService.oauthLogin(socialUser);
            if (oauthLogin.getCode() == 0) {
                MemberResponseVo data = oauthLogin.getData("data", new TypeReference<MemberResponseVo>() {
                });

                log.info("Login success, user info:" + data.toString());

                // first time using session
                session.setAttribute("loginUser", data);

                return "redirect:http://gulimall.com";

            } else {
                return "redirect:http://auth.gulimall.com/login.html";
            }
        } else {
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}