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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class HelloWorldController {

    final WebClient webClient;

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
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        return webClient
                .get()
                .uri("/")
                .retrieve()
                .bodyToMono(List.class)
                .block();

    }

    @GetMapping("/createcustomer")
    public @ResponseBody Customer createcustomer() throws Exception {

        String body = "{\"firstName\":\"jeff\",\"lastName\":\"ellin\"}";
        Customer cu = Customer.builder().firstName("jeff").lastName("ellin").build();
        return webClient
                .post()
                .uri("/")
                .body(Mono.just(cu),Customer.class)
                .retrieve()
                .bodyToMono(Customer.class)
                .block();
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
