package com.example.oauth.controller;

import com.example.oauth.api.Customer;
import com.example.oauth.api.CustomerRepository;
import com.example.oauth.api.CustomerService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    final CustomerService customerService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public  Iterable<Customer> getCustomers(){

        return customerService.getAll();

    }

    @PostMapping("/secure")
    @PreAuthorize("hasRole('ROLE_MANAGER')")

    public Customer createCustomerSecurely(@RequestBody Customer customer){

        return customerService.save(customer);

    }
    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Customer createCustomer(@RequestBody Customer customer){

        return customerService.save(customer);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public void deleteCustomer(@RequestBody String id){

        customerService.delete(id);

    }

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('ROLE_MANAGER')")

    public @ResponseBody Map whoAmI(){

        Map map = new HashMap();
        map.put("hello","world");
        map.put("security",getUserInfo());
        return map;
    }


    private Map getUserInfo(){
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Map result = new HashMap();
        result.put("principal",principal);
        result.put("authorities",authorities);
        return result;
    }

}
