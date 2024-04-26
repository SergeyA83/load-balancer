package org.example.exceptions;

public class AddressExistException extends RuntimeException{
    public AddressExistException() {
        super("Address already exists");
    }
}
