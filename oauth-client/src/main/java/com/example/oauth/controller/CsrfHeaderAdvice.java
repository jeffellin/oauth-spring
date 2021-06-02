package com.example.oauth.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletResponse;

/*
Send CSRF Tokens so that we can send them back
 */

@ControllerAdvice
public class CsrfHeaderAdvice {
    @ModelAttribute("csrf")
    public CsrfToken token(CsrfToken csrfToken, HttpServletResponse response){
        response.setHeader(csrfToken.getHeaderName(),csrfToken.getToken());
        return csrfToken;
    }
}
