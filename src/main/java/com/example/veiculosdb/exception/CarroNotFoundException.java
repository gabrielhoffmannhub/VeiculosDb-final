package com.example.veiculosdb.exception;

public class CarroNotFoundException extends RuntimeException {
    public CarroNotFoundException(String message) {
        super(message);
    }
}