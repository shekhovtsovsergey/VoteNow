package com.example.votenow.exaption;

public abstract class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(String message) {
        super(message);
    }
}
