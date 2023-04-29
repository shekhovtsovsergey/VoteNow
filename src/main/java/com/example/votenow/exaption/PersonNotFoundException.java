package com.example.votenow.exaption;

public class PersonNotFoundException extends ObjectNotFoundException {

    public PersonNotFoundException(Long personId) {
        super(String.format("Person id %s not found", personId));
    }

    public PersonNotFoundException(String message) {
        super(message);
    }
}
