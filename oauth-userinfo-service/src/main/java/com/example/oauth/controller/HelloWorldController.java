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

    @GetMapping("/")
    public @ResponseBody Map<String,Object> sayHello(){

        System.err.println("----wwwww----");

        Map map = new HashMap();
        map.put("hello","world");
        map.put("security",getUserInfo());
        return map;
    }

    @GetMapping("/userInfo")
    public @ResponseBody Map<String,Object> userInfo(){

        System.err.println("----wwwww----");

        Map map = new HashMap();
        map.put("claim_me","jeff");
        map.put("username","jellin");
        map.put("homestore","234");
        map.put("position","manager");
        map.put("sub","a1afeba2-f0d7-447d-950c-e934fe67176e");
        return map;
    }



    private Map getUserInfo(){
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Jwt jtw = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Map result = new HashMap();
        result.put("principal",principal);
        result.put("authorities",authorities);
        return result;
    }

}
