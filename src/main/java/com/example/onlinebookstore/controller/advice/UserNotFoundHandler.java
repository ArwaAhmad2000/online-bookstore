package com.example.onlinebookstore.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.onlinebookstore.dto.ErrorResponse;
import com.example.onlinebookstore.exception.UserNotFoundException;

@ControllerAdvice
public class UserNotFoundHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse er = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "User Not Found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(er);
    }

}
