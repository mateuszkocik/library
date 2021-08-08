package com.matkoc.library.rental;

import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.exception.BookNotFoundException;
import com.matkoc.library.exception.ReaderReachedMaxRentalsException;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentalService{

    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    BookService bookService;

    public Rental save(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void rentBook(Long id, Reader reader) throws BookNotFoundException, ReaderReachedMaxRentalsException{
        Optional<Book> bookO = bookService.getBookById(id);
        if(bookO.isEmpty()) throw new BookNotFoundException(id);
        if(reader.getRentals().stream().filter(r -> !r.finished).count() > 9) throw new ReaderReachedMaxRentalsException(reader.getUser().getUsername());
        Book book = bookO.get();
        if(!book.isAvailable())  book.isReserved()//nie available

    }
}
