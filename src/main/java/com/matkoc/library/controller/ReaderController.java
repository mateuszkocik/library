package com.matkoc.library.controller;

import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.bookdetails.BookDetails;
import com.matkoc.library.bookdetails.BookDetailsService;
import com.matkoc.library.dto.BookDetailsDTO;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import com.matkoc.library.reservation.Reservation;
import com.matkoc.library.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/reader")
public class ReaderController {

  @Autowired
  ReaderService readerService;
  @Autowired
  BookDetailsService bookDetailsService;
  @Autowired
  BookService bookService;
  @Autowired
  ReservationService reservationService;

  @GetMapping
  public String showReaderPage() {
    return "reader";
  }

  @GetMapping("/profile")
  public String showProfile(Model model, Principal principal) {
    Reader reader = readerService.getReaderByEmail(principal.getName());
    System.out.println("resssss");
    for (Reservation res : reader.getReservations()) {
      System.out.println(res.getId());
    }
    model.addAttribute("reader", reader);
    return "profile";
  }

  @GetMapping("/search")
  public String showSearchPage(Model model){
    model.addAttribute("results", new ArrayList<BookDetails>());
    model.addAttribute("bookDetails", new BookDetailsDTO());
    return "search";
  }

  @PostMapping("/search")
  public ModelAndView viewResults(
          @ModelAttribute("results") ArrayList<BookDetails> results,
          @ModelAttribute("bookDetails") BookDetailsDTO bookDetails) {
    ModelAndView modelAndView = new ModelAndView("search");
    results = bookDetailsService.findBookDetails(bookDetails);
    modelAndView.addObject("results", results);
    modelAndView.addObject("bookDetails", bookDetails);

    return modelAndView;
  }

  @GetMapping("/bookdetails")
  public ModelAndView showBookDetails(@RequestParam Long id){
    ModelAndView modelAndView = new ModelAndView("reader_book");
    ArrayList<Book> books = bookService.findBooksWithBookDetails(id);
    BookDetails bookDetails = bookDetailsService.findById(id);
    modelAndView.addObject("books", books);
    modelAndView.addObject("bookDetails", bookDetails);
    return modelAndView;
  }

  @PostMapping("/reservebook")
  public ModelAndView reserveBook(
      @RequestParam Long bookId,
      @ModelAttribute("bookDetails") BookDetails bookDetails,
      Principal principal) {
    ModelAndView modelAndView = new ModelAndView("reader_book");
    modelAndView.addObject("bookDetails", bookDetails);

    Optional<Book> bookO = bookService.findBookById(bookId);
    if (bookO.isEmpty()) return modelWithMessage(modelAndView, "Book with that id does not exist.");

    Book book = bookO.get();
    if (!book.isAvailable()) return modelWithMessage(modelAndView, "Book is not available.");

    Reader reader = readerService.getReaderByEmail(principal.getName());
    if (!readerService.readerHasLessThan5Reservations(reader))
      return modelWithMessage(modelAndView, "Can not reserve more than 5 books.");

    Reservation reservation = reservationService.createReservation(book, reader);
//    reader.getReservations().add(reservation);
//    readerService.save(reader);
    return modelWithMessage(modelAndView, "Reservation succeeded.");
  }

  private ModelAndView modelWithMessage(ModelAndView modelAndView, String message){
    return modelAndView.addObject("message", message);
  }
}
