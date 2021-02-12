package com.example.examapp.demo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private long userId;
    private String userName;
    private String email;
    private String firstName;
    private String  lastName;

}
