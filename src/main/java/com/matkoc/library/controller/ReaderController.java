package com.matkoc.library.controller;

import com.matkoc.library.algorithm.GaleShapley;
import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.book.BookStatus;
import com.matkoc.library.bookdetails.BookDetails;
import com.matkoc.library.bookdetails.BookDetailsService;
import com.matkoc.library.dto.BookDetailsDTO;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import com.matkoc.library.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

import static com.matkoc.library.book.BookStatus.AVAILABLE;

@Controller
@RequestMapping("/reader")
public class ReaderController {

  private final String viewPrefix = "/reader/";

  @Autowired
  ReaderService readerService;
  @Autowired
  BookDetailsService bookDetailsService;
  @Autowired
  BookService bookService;
  @Autowired
  ReservationService reservationService;
  @Autowired
  GaleShapley galeShapley;

  @GetMapping
  public String showReaderPage() {
    galeShapley.runAlgorithm();
    return viewPrefix + "reader";
  }

  @GetMapping("/profile")
  public String showProfile(Model model, Principal principal) {
    Reader reader = readerService.getReaderByEmail(principal.getName());
    model.addAttribute("reader", reader);
    return viewPrefix + "profile";
  }

  @PostMapping("/profile/cancelReservation")
  public ModelAndView cancelReservation(Principal principal, @RequestParam(value = "id") Long id) {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "profile");
    Reader reader = readerService.getReaderByEmail(principal.getName());
    modelAndView.addObject("reader", reader);
    reservationService.cancelReservation(id);
    return modelAndView;
  }

  @GetMapping("/search")
  public String showSearchPage(Model model){
    model.addAttribute("results", new ArrayList<BookDetails>());
    model.addAttribute("bookDetails", new BookDetailsDTO());
    return viewPrefix + "search";
  }

  @PostMapping("/search")
  public ModelAndView viewResults(
          @ModelAttribute("results") ArrayList<BookDetails> results,
          @ModelAttribute("bookDetails") BookDetailsDTO bookDetails) {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "search");
    results = bookDetailsService.findBookDetails(bookDetails);
    modelAndView.addObject("results", results);
    modelAndView.addObject("bookDetails", bookDetails);

    return modelAndView;
  }

  @GetMapping("/bookdetails")
  public ModelAndView showBookDetails(@RequestParam Long id){
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "reader_book");
    BookDetails bookDetails = bookDetailsService.findById(id);
    modelAndView.addObject("bookDetails", bookDetails);
    HashMap<BookStatus, Long> bookStatusCount = getBookStatusCount(bookDetails);
    return bookStatusCount.get(AVAILABLE) > 0
        ? modelAndView.addObject("available", true)
        : modelAndView;
  }

  private HashMap<BookStatus, Long> getBookStatusCount(BookDetails bookDetails) {
    Collection<Book> books = bookDetails.getBooks();
    HashMap<BookStatus, Long> statusCount = new HashMap<>();
    for(BookStatus bs : BookStatus.values()) {
      Long amount = books.stream()
              .filter(b -> b.getBookStatus().equals(bs))
              .count();
      statusCount.put(bs, amount);
    }
    return statusCount;
  }

  @PostMapping("/bookdetails")
  public ModelAndView reserveBook(
      @RequestParam(value = "id") Long id,
      Principal principal) {
    BookDetails bookDetails = bookDetailsService.findById(id);
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "reader_book");
    modelAndView.addObject("bookDetails", bookDetails);

    Optional<Book> book = getFirstAvailable(bookDetails);
    if(book.isEmpty()) return modelWithMessage(modelAndView, "No available books");

    Reader reader = readerService.getReaderByEmail(principal.getName());
    if (!readerService.readerHasLessThan5Reservations(reader))
      return modelWithMessage(modelAndView, "Can not reserve more than 5 books.");

    reservationService.createReservation(book.get(), reader);

    return modelWithMessage(modelAndView, "Reservation succeeded.");
  }

  private Optional<Book> getFirstAvailable(BookDetails bookDetails){
    return bookDetails.getBooks().stream().filter(Book::isAvailable).findAny();
  }

  private ModelAndView modelWithMessage(ModelAndView modelAndView, String message){
    return modelAndView.addObject("message", message);
  }
}
