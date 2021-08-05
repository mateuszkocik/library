package com.matkoc.library.reservation;

import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.book.BookStatus;
import com.matkoc.library.reader.Reader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {

  @Autowired
  public ReservationRepository reservationRepository;
  @Autowired
  public BookService bookService;

  public Reservation createReservation(Book book, Reader reader){
    LocalDate toDate = LocalDate.now().plusWeeks(1);
    book.setBookStatus(BookStatus.RESERVED);
    Reservation reservation = new Reservation(book, reader, toDate);
    bookService.save(book);
    return reservationRepository.save(reservation);
  }
}
