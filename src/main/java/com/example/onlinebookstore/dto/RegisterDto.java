package com.example.onlinebookstore.dto;

import lombok.Data;

@Data
public class RegisterDto {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;

}
