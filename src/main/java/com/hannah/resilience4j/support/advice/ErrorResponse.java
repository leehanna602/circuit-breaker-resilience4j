package com.hannah.resilience4j.support.advice;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String name;
    private String message;
}
