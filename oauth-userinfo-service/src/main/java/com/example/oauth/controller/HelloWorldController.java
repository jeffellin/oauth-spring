package com.example.oauth.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@RestController
@PreAuthorize("hasAuthority('ROLE_USER')") // This works as expected

public class HelloWorldController {

    RestTemplateBuilder restTemplateBuilder;




    @GetMapping("/userInfo")
    public @ResponseBody Map<String,Object> userInfo(){
        String sub = getSub();
        String userName = getUserName();
        Map userInfo = getUserInfo(userName);
        userInfo.put("username",userName);
        userInfo.put("sub",sub);
        return userInfo;
    }

    private String getSub(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaim("sub");
    }

    private String getUserName(){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaimAsString("username");
    }

    private Map getUserInfo(String userName){

        //do something meaningful here.

        Map map = new HashMap();
        map.put("claim_me","jeff");
        map.put("username",userName);
        map.put("homestore","234");
        map.put("position","manager");
        map.put("sub","a1afeba2-f0d7-447d-950c-e934fe67176e");
        return map;
    }

}
