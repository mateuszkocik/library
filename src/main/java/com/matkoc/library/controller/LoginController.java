package com.matkoc.library.controller;

import com.matkoc.library.dto.BookDetailsDTO;
import com.matkoc.library.dto.PasswordDTO;
import com.matkoc.library.security.User;
import com.matkoc.library.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller("/login")
public class LoginController{

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/inactive")
    public String showChangePasswordForm(Model model, Principal principal){
        model.addAttribute("user", userDetailsService.loadUserByUsername(principal.getName()));
        return "inactive";
    }

  @PostMapping("/inactive")
  public ModelAndView processPasswordChange(
          @ModelAttribute("user") User user,
          @ModelAttribute("password") @Valid PasswordDTO passwordDTO, BindingResult result) {
      ModelAndView modelAndView = new ModelAndView("inactive", "password", passwordDTO);
      if(result.hasErrors()) return modelAndView;
      if(passwordsAreNotMatching(passwordDTO)){
          modelAndView.addObject("message", "Passwords are not matching");
          return modelAndView;
      }
      userDetailsService.activateUser(user, passwordDTO.getPassword());

      return new ModelAndView("/reader");
  }

  private boolean passwordsAreNotMatching(PasswordDTO passwordDTO){
        return !passwordDTO.getPassword().equals(passwordDTO.getMatchingPassword());
  }

}
