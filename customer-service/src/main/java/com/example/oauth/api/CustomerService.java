package com.example.oauth.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    public Optional<Customer> findById(String id){
        return customerRepository.findById(id);
    }

    public void delete(String customer){
        customerRepository.deleteById(customer);
    }
}
