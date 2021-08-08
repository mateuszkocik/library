package com.matkoc.library.exception;

public class BookNotFoundException extends Exception{
    public BookNotFoundException(Long bookId){
        super("Book with id: " + bookId + " does not exists.");
    }
}
