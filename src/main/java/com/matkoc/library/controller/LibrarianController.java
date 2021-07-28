package com.matkoc.library.controller;

import com.matkoc.library.dto.RentalDTO;
import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.exception.UserAlreadyExistException;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import com.matkoc.library.rental.RentalService;
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

  private final ReaderService readerService;
  private final RentalService rentalService;

  @Autowired
  public LibrarianController(ReaderService readerService, RentalService rentalService){
    this.readerService = readerService;
    this.rentalService = rentalService;
  }

  @GetMapping
  public String showLibrarianPage(){
    return "librarian";
  }

  @GetMapping("/add-user")
  public String showNewUserForm(Model model) {
    UserDTO userDTO = new UserDTO();
    model.addAttribute("user", userDTO);

    return "add_user";
  }

  @PostMapping("/add-user")
  public ModelAndView registerUserAccount(
      @ModelAttribute("user") @Valid UserDTO userDto,
      BindingResult result) {
    ModelAndView modelAndView = new ModelAndView("add_user", "user", userDto);
    if(result.hasErrors()) return modelAndView;

    try {
      readerService.registerNewReader(userDto);
    } catch (UserAlreadyExistException e) {
      modelAndView.addObject("message", "An account for that username/email already exists.");
      return modelAndView;
    }

    return new ModelAndView("/librarian");
  }

  @GetMapping("/check-profile")
  public String showReaderProfile(@RequestParam(value = "readerEmail") String readerEmail, Model model){
    Reader reader = readerService.getReaderByEmail(readerEmail);
    model.addAttribute("reader", reader);

    return "check_profile";
  }


//
//  @GetMapping("/rent-book")
//  public String showRentBookPage(Model model){
//    RentalDTO rentalDTO = new RentalDTO();
//    model.addAttribute("rental", rentalDTO);
//
//    return "rent_book";
//  }
//
//  @PostMapping("/rent-book")
//  public ModelAndView makeRental(@ModelAttribute("rental") @Valid RentalDTO rentalDTO, BindingResult result) {
//    ModelAndView modelAndView = new ModelAndView("rent_book", "rental", rentalDTO);
//
//    if(result.hasErrors()) return modelAndView;
//
//    try {
//      rentalService.makeNewRental(rentalDTO);
//    } catch (UserAlreadyExistException e) {
//      modelAndView.addObject("message", "An account for that username/email already exists.");
//      return modelAndView;
//    }
//
//  }

  @GetMapping("/tasks")
  public String showTasks() {
    return "tasks";
  }

  @GetMapping("/add-book")
  public String showAddBookForm() {
    return "add-book";
  }

  @GetMapping("/add-book-details")
  public String showAddBookDetailsForm() {
    return "add-book-detail";
  }
}
