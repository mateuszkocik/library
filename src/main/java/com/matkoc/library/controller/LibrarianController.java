package com.matkoc.library.controller;

import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.reader.ReaderService;
import com.matkoc.library.security.User;
import com.matkoc.library.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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

  @GetMapping("/add-user")
  public String showNewUserForm(Model model) {
    UserDTO userDTO = new UserDTO();
    model.addAttribute("user", userDTO);

    return "add_user";
  }

  @PostMapping("/add-user")
  public ModelAndView registerUserAccount(
      @ModelAttribute("user") UserDTO userDto, HttpServletRequest request, Errors errors) {
    ModelAndView modelAndView = new ModelAndView("home");
    try {
      userDetailsService.registerNewUser(userDto);
      //            User registered = userService.registerNewUserAccount(userDto);
    } catch (Exception uaeEx) {
      System.out.println("EEEEEEERRRRRRRRRRRRRRRRRRROOOOOOOOOOOOORRRRRRRRRRR");
      //            mav.addObject("message", "An account for that username/email already exists.");
    }
    return modelAndView;
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
