package com.matkoc.library.controller;

import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.exception.UserAlreadyExistException;
import com.matkoc.library.reader.ReaderService;
import com.matkoc.library.security.User;
import com.matkoc.library.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/librarian")
public class LibrarianController {

  private final UserDetailsServiceImpl userDetailsService;
  private final ReaderService readerService;

  @Autowired
  public LibrarianController(UserDetailsServiceImpl userDetailsService, ReaderService readerService){
    this.userDetailsService = userDetailsService;
    this.readerService = readerService;
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
      BindingResult result,
      Errors errors) {
    ModelAndView modelAndView = new ModelAndView("add_user", "user", userDto);
    if(result.hasErrors()) return modelAndView;

    try {
      userDetailsService.registerNewUser(userDto);
    } catch (UserAlreadyExistException e) {
      modelAndView.addObject("message", "An account for that username/email already exists.");
      return modelAndView;
    }

    return new ModelAndView("/librarian");
  }

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
