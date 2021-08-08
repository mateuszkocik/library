package com.matkoc.library.exception;

public class ReaderReachedMaxRentalsException extends Exception{
    public ReaderReachedMaxRentalsException(String readerEmail){
        super("Reader " + readerEmail + " reached maximum limit (10) borrowed books.");
    }
}
