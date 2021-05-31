package com.example.oauth.controller;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String id;
    private String firstName;
    private String lastName;

}
