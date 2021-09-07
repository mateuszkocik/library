package com.matkoc.library.controller;

import com.matkoc.library.book.Book;
import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.exception.UserAlreadyExistException;
import com.matkoc.library.librarian.LibrarianService;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.rental.RentalService;
import com.matkoc.library.tasks.GaleShapley;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/manager")
public class ManagerController {

  private final String viewPrefix = "/manager/";
  @Autowired
  private LibrarianService librarianService;
  @Autowired
  private RentalService rentalService;
  @Lazy
  @Autowired
  private GaleShapley galeShapley;

  @GetMapping()
  public ModelAndView showManagerPage() {
    return new ModelAndView(viewPrefix + "manager");
  }

  @GetMapping("/add-librarian")
  public ModelAndView addLibrarianPage() {
    UserDTO userDTO = new UserDTO();
    userDTO.setPassword(generateRandomPassword());
    return new ModelAndView(viewPrefix + "add_librarian", "librarian", userDTO);
  }

  private String generateRandomPassword() {
    return RandomStringUtils.randomAlphabetic(10);
  }

  @PostMapping("/add-librarian")
  public ModelAndView registerLibrarianAccount(
      @ModelAttribute("librarian") @Valid UserDTO userDto, BindingResult result) {
    ModelAndView modelAndView =
        new ModelAndView(viewPrefix + "add_librarian", "librarian", userDto);
    if (result.hasErrors()) return modelAndView;

    try {
      librarianService.registerNewLibrarian(userDto);
    } catch (UserAlreadyExistException e) {
      modelAndView.addObject("message", "An account for that username/email already exists.");
      return modelAndView;
    }

    return new ModelAndView(viewPrefix + "/manager");
  }

  @GetMapping("/remove-librarian")
  public ModelAndView removeLibrarianPage() {
    UserDTO userDTO = new UserDTO();
    return new ModelAndView(viewPrefix + "remove_librarian", "librarian", userDTO);
  }

  @PostMapping("/remove-librarian")
  public ModelAndView removeLibrarianPost(@ModelAttribute("librarian") UserDTO userDTO) {
    try{
      librarianService.removeLibrarian(userDTO.getEmail());
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }
    return new ModelAndView(viewPrefix + "remove_librarian", "librarian", userDTO);
  }


  @GetMapping("/statistics")
  public ModelAndView statisticsPage() {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "statistics");
    Map<String, Long> beforeSort = rentalService.getRentalAmountInPreviousYear();
    TreeMap<String, Long> sorted = new TreeMap<>(beforeSort);
    return modelAndView.addObject("rentalCounterData", sorted);
  }

  @GetMapping("/suggested-books")
  public ModelAndView showSuggestedBooks() {
    ModelAndView modelAndView = new ModelAndView(viewPrefix + "suggested_books");
    HashMap<Reader, Book> suggestedBooks = galeShapley.getSuggestions();
    modelAndView.addObject("suggestedBooks", suggestedBooks);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd");
    modelAndView.addObject("updateTime", galeShapley.getLastUpdateTime().withHour(0).withMinute(0).withSecond(0).format(formatter));
    return modelAndView;
  }
}
