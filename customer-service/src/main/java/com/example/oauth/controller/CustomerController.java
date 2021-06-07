package com.example.oauth.controller;

import com.example.oauth.api.Customer;
import com.example.oauth.api.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "contact", description = "the Contact API")

public class CustomerController {

    final CustomerService customerService;


    @Operation(summary = "Get a Customer by its id", tags = { "customer" }, description = "Find a Book by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the book",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found",
                    content = @Content) })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public  Optional<Customer> getCustomers(@PathVariable() String id){

        return customerService.findById(id);

    }



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
    @Operation(security = {@SecurityRequirement(name="spring_oauth"),
            @SecurityRequirement(name="bearer-token")})

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
