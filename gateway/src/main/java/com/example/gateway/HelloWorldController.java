package com.example.gateway;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@RestController
public class HelloWorldController {

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public Mono<String> sayHello(){

         return Mono.just("I am a mananger");
    }

}
