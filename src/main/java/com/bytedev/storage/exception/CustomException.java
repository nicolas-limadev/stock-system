package com.bytedev.storage.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message, Exception e){
        super(message, e);
    }
}
