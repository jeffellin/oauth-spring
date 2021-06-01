package com.example.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {


    final RestTemplateBuilder restTemplateBuilder;

    @GetMapping("/")
    //@PreAuthorize("principal.getClaim('username')=='jeff@ellin.com'")
    public @ResponseBody Map<String,Object> sayHello(){
        DefaultOidcUser principal = (DefaultOidcUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        principal.getClaim("username");
        System.err.println("----wwwww----");

        Map map = new HashMap();
        map.put("hello","world");
        map.put("prin", principal.getClaim("username"));
        map.put("security",getUserInfo());
        return map;
    }

    @GetMapping("/customer")
    public @ResponseBody  List<Customer> addCustomer() throws Exception {
        RestTemplate rt = restTemplateBuilder.additionalInterceptors().build();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return rt.exchange("http://localhost:8083/",HttpMethod.GET,httpEntity,List.class ).getBody();
        //return Customer.builder().firstName("jeff").lastName("ellin").build();
    }

    @GetMapping("/createcustomer")
    public @ResponseBody Customer createcustomer() throws Exception {
        RestTemplate rt = restTemplateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String body = "{\"firstName\":\"jeff\",\"lastName\":\"ellin\"}";
        HttpEntity<String> httpEntity = new HttpEntity<>(body,headers);
        Customer foo = rt.postForObject(new URI("http://localhost:8083/"), httpEntity,Customer.class);
        return foo;
    }

    private String getToken(){
        DefaultOidcUser o = (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return o.getIdToken().getTokenValue();
    }
    private Map getUserInfo(){
        String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Collection authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Map result = new HashMap();
        result.put("principal",principal);
        result.put("authorities",authorities);
        return result;
    }

}
