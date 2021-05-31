package com.example.oauth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerService {
    final CustomerRepository customerRepository;

    public Iterable<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Customer save(Customer customer){

      return  customerRepository.save(customer);

    }

    public void delete(String customer){
        customerRepository.deleteById(customer);
    }
}
