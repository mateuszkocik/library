package com.matkoc.library.reservation;

import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.book.BookStatus;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.rental.Rental;
import com.matkoc.library.rental.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;
  @Autowired
  private BookService bookService;
  @Autowired
  private RentalService rentalService;

  public Reservation createReservation(Book book, Reader reader){
    LocalDate toDate = LocalDate.now().plusWeeks(1);
    book.setBookStatus(BookStatus.RESERVED);
    Reservation reservation = new Reservation(book, reader, toDate);
    bookService.save(book);
    return reservationRepository.save(reservation);
  }

  public void acceptReservation(Long id) { //ustawic sprawdzanie czy ma mniej niz 10
    Reservation reservation = reservationRepository.findById(id).get();
    Book book = reservation.getBook();
    LocalDate fromDate = LocalDate.now();
    LocalDate toDate = fromDate.plusMonths(1);
    Rental rental = new Rental();
    rental.setBook(book);
    rental.setReader(reservation.getReader());
    rental.setFromDate(fromDate);
    rental.setToDate(toDate);
    rental.setFinished(false);

    rentalService.save(rental);
    cancelReservation(id);
    bookService.setBookStatus(book, BookStatus.BORROWED);
  }

  public void cancelReservation(Long id) {
    Reservation reservation = reservationRepository.findById(id).get();
    bookService.setBookStatus(reservation.getBook(), BookStatus.AVAILABLE);
    reservationRepository.delete(reservation);
  }
}
