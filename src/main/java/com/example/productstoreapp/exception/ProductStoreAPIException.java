package com.example.productstoreapp.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Getter
@Setter
public class ProductStoreAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public ProductStoreAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ProductStoreAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}
