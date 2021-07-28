package com.matkoc.library.rental;

import com.matkoc.library.book.BookService;
import com.matkoc.library.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService{

    RentalRepository rentalRepository;
    BookService bookService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, BookService bookService){
        this.rentalRepository = rentalRepository;
        this.bookService = bookService;
    }
}
