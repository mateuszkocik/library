package com.matkoc.library.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String email) {
        super("User with email: " + email + " already exists");
    }
}
