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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/login")
public class LoginController{

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/activate-account")
    public String showChangePasswordForm(Model model){
        model.addAttribute("password", new PasswordDTO());
        return "activate_account";
    }

  @PostMapping("/activate-account")
  public ModelAndView processPasswordChange(
          Principal principal,
          @ModelAttribute("password") @Valid PasswordDTO passwordDTO, BindingResult result) {
      ModelAndView modelAndView = new ModelAndView("activate_account", "password", passwordDTO);
      System.out.println("1");
      if(result.hasErrors()) return modelAndView;
      System.out.println("2");
      if(passwordsAreNotMatching(passwordDTO)){
          modelAndView.addObject("message", "Passwords are not matching");
          System.out.println("3");
          return modelAndView;
      }
      userDetailsService.activateUser(principal.getName(), passwordDTO.getPassword());
      System.out.println("4");

      return new ModelAndView("/reader");
  }

  private boolean passwordsAreNotMatching(PasswordDTO passwordDTO){
        return !passwordDTO.getPassword().equals(passwordDTO.getMatchingPassword());
  }

}
