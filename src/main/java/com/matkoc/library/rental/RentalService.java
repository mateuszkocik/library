package com.matkoc.library.rental;

import com.matkoc.library.book.BookService;
import com.matkoc.library.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService{

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    BookService bookService;

    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }
}
