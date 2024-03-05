package com.example.onlinebookstore.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {

    private final int statusCode;
    private final String error;
    private final String message;

}
