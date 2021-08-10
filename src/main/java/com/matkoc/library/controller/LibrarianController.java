package com.matkoc.library.controller;

import com.matkoc.library.book.Book;
import com.matkoc.library.book.BookService;
import com.matkoc.library.book.BookStatus;
import com.matkoc.library.bookdetails.BookDetails;
import com.matkoc.library.bookdetails.BookDetailsService;
import com.matkoc.library.dto.BookDetailsDTO;
import com.matkoc.library.dto.IdDTO;
import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.exception.UserAlreadyExistException;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import com.matkoc.library.rental.RentalService;
import com.matkoc.library.reservation.ReservationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

  private final String viewPrefix = "/librarian/";
  @Autowired
  private ReaderService readerService;
  @Autowired
  private RentalService rentalService;
  @Autowired
  private BookDetailsService bookDetailsService;
  @Autowired
  private ReservationService reservationService;
  @Autowired
  private BookService bookService;

  @GetMapping
  public String showLibrarianPage(){
    return viewPrefix + "librarian";
  }

  @GetMapping("/add-user")
  public String showNewUserForm(Model model) {
    UserDTO userDTO = new UserDTO();
    userDTO.setPassword(generateRandomPassword());
    model.addAttribute("user", userDTO);

    return viewPrefix + "add_user";
  }

  private String generateRandomPassword(){
    return RandomStringUtils.randomAlphabetic(10);
  }

  @PostMapping("/add-user")
  public ModelAndView registerUserAccount(
      @ModelAttribute("user") @Valid UserDTO userDto,
      BindingResult result) {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "add_user", "user", userDto);
    if(result.hasErrors()) return modelAndView;

    try {
      readerService.registerNewReader(userDto);
    } catch (UserAlreadyExistException e) {
      modelAndView.addObject("message", "An account for that username/email already exists.");
      return modelAndView;
    }

    return new ModelAndView(viewPrefix + "/librarian");
  }

  @GetMapping("/add-book-details")
  public String showAddBookDetailsForm(Model model) {
    model.addAttribute("bookDetails", new BookDetailsDTO());
    return viewPrefix + "add_book_detail";
  }

  @PostMapping("/add-book-details")
  public ModelAndView addBookDetails(
          @ModelAttribute("bookDetails") @Valid BookDetailsDTO bookDetailsDTO, BindingResult result) {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "add_book_detail", "bookDetails", bookDetailsDTO);
    if(result.hasErrors()) return modelAndView;

    try {
      bookDetailsService.addBookDetails(bookDetailsDTO);
    } catch (Exception e) {
      modelAndView.addObject("message", "Failed to add book details");
      return modelAndView;
    }

    return new ModelAndView(viewPrefix + "/librarian");
  }

  @GetMapping("/check-profile/{email}")
  public ModelAndView showReaderProfileDetails(@PathVariable(value = "email") String readerEmail) {
    return buildCheckProfileMAV(readerEmail);
  }

  @PostMapping("/check-profile/{email}/accept-reservation/{id}")
  public ModelAndView acceptReservation(
      @PathVariable(value = "email") String readerEmail,
      @PathVariable(value = "id") Long id) {
    reservationService.acceptReservation(id);
    return buildCheckProfileMAV(readerEmail);
  }

  @PostMapping("/check-profile/{email}/rent-book")
  public ModelAndView rentSpecificBook(
      @PathVariable(value = "email") String readerEmail,
      @ModelAttribute("inputBookId") @Valid IdDTO bookId) {
    ModelAndView modelAndView = buildCheckProfileMAV(readerEmail);
    Reader reader = readerService.getReaderByEmail(readerEmail);
    try{
      rentalService.rentBook(bookId.getId(), reader);
    }catch(Exception e) {
      return modelWithMessage(modelAndView, e.getMessage());
    }
    return modelWithMessage(modelAndView, "Book rented successfully");
  }

  @PostMapping("/check-profile/{email}/return-book/{rental-id}")
  public ModelAndView returnBook(
      @PathVariable(value = "email") String readerEmail,
      @PathVariable(value = "rental-id") Long rentalId) {
    rentalService.finishRental(rentalService.getRentalById(rentalId));
    return buildCheckProfileMAV(readerEmail);
  }

  @GetMapping("/search")
  public ModelAndView showSearchPage(){
    return buildSearchMAV();
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

  @GetMapping("/bookdetails/{id}")
  public ModelAndView showBookDetails(@PathVariable Long id){
    return buildBookDetailsMAV(id);
  }

  @PostMapping("/bookdetails/{bookDetailsId}/delete")
  public ModelAndView deleteBookDetails(@PathVariable(value = "bookDetailsId") Long bookDetailsId){
    bookDetailsService.deleteById(bookDetailsId);
    return buildSearchMAV();
  }

  @PostMapping("/bookdetails/{bookDetailsId}/delete-book")
  public ModelAndView addBookToBookDetails(
      @PathVariable Long bookDetailsId, @ModelAttribute("deleteBookId") @Valid IdDTO deleteBookId) {
    ModelAndView modelAndView = buildBookDetailsMAV(bookDetailsId);
    bookService.deleteBook(deleteBookId.getId());
    return modelAndView.addObject("deletedBookId", deleteBookId.getId());
  }

  @PostMapping("/bookdetails/{id}/add")
  public ModelAndView addBookToBookDetails(@PathVariable Long id) {
    ModelAndView modelAndView = buildBookDetailsMAV(id);
    Book newBook = bookService.addNewBookToBookDetails(bookDetailsService.findById(id));
    return modelAndView.addObject("newBookId", newBook.getId());
  }

  private ModelAndView modelWithMessage(ModelAndView modelAndView, String message){
    return modelAndView.addObject("message", message);
  }

  public ModelAndView buildSearchMAV() {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "search");
    modelAndView.addObject("results", new ArrayList<BookDetails>());
    modelAndView.addObject("bookDetails", new BookDetailsDTO());
    return modelAndView;
  }

  private ModelAndView buildBookDetailsMAV(Long bookDetailsId) {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "librarian_book");
    BookDetails bookDetails = bookDetailsService.findById(bookDetailsId);
    modelAndView.addObject("bookDetails", bookDetails);
    modelAndView.addObject("deleteBookId", new IdDTO());
    HashMap<BookStatus, Long> bookStatusCount = getBookStatusCount(bookDetails);
    return modelAndView;
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

  private ModelAndView buildCheckProfileMAV(String readerEmail) {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "check_profile");
    modelAndView.addObject("inputBookId", new IdDTO());
    Reader reader = readerService.getReaderByEmail(readerEmail);
    if (StringUtils.isNotBlank(readerEmail) && reader == null)
      return modelWithMessage(
              modelAndView, "Reader with email: " + readerEmail + " does not exists.");
    modelAndView.addObject("reader", reader);
    return modelAndView;
  }
}
