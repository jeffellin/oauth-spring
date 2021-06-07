package com.example.oauth.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Schema(description = "First Name of the contact.",
            example = "Jessica", required = true)
    private String firstName;
    private String lastName;

}
