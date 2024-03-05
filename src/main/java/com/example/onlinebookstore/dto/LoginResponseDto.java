package com.example.onlinebookstore.dto;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String email;
    private String token;

}
