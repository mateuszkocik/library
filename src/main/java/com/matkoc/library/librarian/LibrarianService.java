package com.matkoc.library.librarian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrarianService{
    private final LibrarianRepository librarianRepository;

    @Autowired
    public LibrarianService(LibrarianRepository librarianRepository){
        this.librarianRepository = librarianRepository;
    }
}
