package com.example.notification_hub.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice // Spring ko bola: "Pore project mein kahin bhi error aaye, idhar aao"
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class) // Sirf validation errors ko catch karne ke liye
    public ResponseEntity<Map<String, String>> handleValidationErrors(ConstraintViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();

        // Error message ko clean karke map mein daal rahe hain
        ex.getConstraintViolations().forEach(violation -> {
            String propertyName = violation.getPropertyPath().toString();
            // Sirf parameter ka naam nikalne ke liye (e.g., "to")
            String paramName = propertyName.substring(propertyName.lastIndexOf('.') + 1);
            errorMap.put(paramName, violation.getMessage());
        });

        // 500 ki jagah hum khud 400 Bad Request bhej rahe hain custom JSON ke sath
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }
}