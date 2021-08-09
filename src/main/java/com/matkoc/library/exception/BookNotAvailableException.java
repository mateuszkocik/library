package com.matkoc.library.exception;

public class BookNotAvailableException extends Exception{
    public BookNotAvailableException(Long bookId){
        super("Book with id: " + bookId + " is not available");
    }
}
