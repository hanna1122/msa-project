package com.project.payment.exception;

import com.google.gson.Gson;
import com.project.payment.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class OrderException {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> ExceptionHandler(Exception e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("500")
                .message(e.getMessage())
                .build();
        System.out.println(errorResponse);
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        Gson gson = new Gson();
        Map<String, String> map = bindingResult.getFieldErrors().stream().collect(
                Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        String msg = gson.toJson(map);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message(msg)
                .build();
        return ResponseEntity.ok(errorResponse);
    }
}
