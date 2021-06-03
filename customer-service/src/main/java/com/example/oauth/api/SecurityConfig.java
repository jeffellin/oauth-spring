package com.example.oauth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig  {


    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(this.getConverter());
        return http.build();
    }


    @Bean
    public JwtAuthenticationConverter getConverter(){
        JwtAuthenticationConverter jwtConverter =  new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new CustomJWTAuthorityConverter());
        return jwtConverter;
    }

  class CustomJWTAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>>{

      @Override
      public Collection<GrantedAuthority> convert(Jwt jwt) {

          //call service here
         System.err.println("its me");
          return Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER"));
      }
  }

    

}
