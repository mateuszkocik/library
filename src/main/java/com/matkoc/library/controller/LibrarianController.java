package com.matkoc.library.controller;

import com.matkoc.library.bookdetails.BookDetailsService;
import com.matkoc.library.dto.BookDetailsDTO;
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

  @GetMapping("/check-profile")
  public ModelAndView showReaderProfileDetails(@RequestParam(value = "email") String readerEmail) {
    return buildCheckProfileMAV(readerEmail);
  }

  private ModelAndView modelWithMessage(ModelAndView modelAndView, String message){
    return modelAndView.addObject("message", message);
  }

  @PostMapping("/check-profile?email={email}/accept-reservation")
  public ModelAndView acceptReservation(
      @PathVariable(value = "email") String readerEmail,
      @RequestParam(value = "id") Long id) {
    reservationService.acceptReservation(id);
    return buildCheckProfileMAV(readerEmail);
  }

  private ModelAndView buildCheckProfileMAV(String readerEmail) {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "check_profile");
    Reader reader = readerService.getReaderByEmail(readerEmail);
    if (StringUtils.isNotBlank(readerEmail) && reader == null)
      return modelWithMessage(
              modelAndView, "Reader with email: " + readerEmail + " does not exists.");
    modelAndView.addObject("reader", reader);
    return modelAndView;
  }
}
