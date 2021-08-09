package com.matkoc.library.rental;

import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.book.BookStatus;
import com.matkoc.library.exception.BookNotAvailableException;
import com.matkoc.library.exception.BookNotFoundException;
import com.matkoc.library.exception.ReaderReachedMaxRentalsException;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import com.matkoc.library.reservation.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalService {

  @Autowired RentalRepository rentalRepository;
  @Autowired BookService bookService;

  public Rental save(Rental rental) {
    return rentalRepository.save(rental);
  }

  public Rental getRentalById(Long id) {
    return rentalRepository.findById(id).get();
  }

  public Rental rentBook(Long id, Reader reader)
      throws BookNotFoundException, ReaderReachedMaxRentalsException, BookNotAvailableException {
    Optional<Book> bookO = bookService.getBookById(id);
    if (bookO.isEmpty()) throw new BookNotFoundException(id);
    if (reader.getRentals().stream().filter(r -> !r.finished).count() > 9)
      throw new ReaderReachedMaxRentalsException(reader.getUser().getUsername());
    Book book = bookO.get();
    if (!(book.isAvailable()
        || (book.isReserved() && !book.getReservation().getReader().equals(reader))))
      throw new BookNotAvailableException(id);
    LocalDate fromDate = LocalDate.now();
    LocalDate toDate = fromDate.plusMonths(1);
    Rental rental = new Rental(book, reader, fromDate, toDate, null, false);
    book.setBookStatus(BookStatus.BORROWED);
    bookService.save(book);
    return rentalRepository.save(rental);
  }

  public void finishRental(Rental rental) {
    rental.setFinished(true);
    rental.setReturnDate(LocalDate.now());
    rental.getBook().setBookStatus(BookStatus.AVAILABLE);
    rentalRepository.save(rental);
  }
}
