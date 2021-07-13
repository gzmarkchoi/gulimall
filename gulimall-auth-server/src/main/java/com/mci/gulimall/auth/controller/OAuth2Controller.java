package com.mci.gulimall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.mci.common.utils.HttpUtils;
import com.mci.gulimall.auth.vo.SocialUser;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Login using Social media
 */
@Controller
public class OAuth2Controller {
    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code) throws Exception {

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
            // Get access token
            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);

            // Get social user
            // 1. if first time login, register user with the social user info

        } else {
            return "redirect:http://auth.gulimall.com/login.html";
        }

        return "redirect:http://gulimall.com";
    }
}