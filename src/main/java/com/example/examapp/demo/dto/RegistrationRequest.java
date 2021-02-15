package com.example.examapp.demo.dto;

import com.example.examapp.demo.config.security.ApplicationUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegistrationRequest {

    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String email;

}
