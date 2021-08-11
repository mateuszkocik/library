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

    private final String viewPrefix = "/security/";

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @GetMapping
    public String showLoginForm(){
        return viewPrefix + "login";
    }

    @GetMapping("/activate-account")
    public String showChangePasswordForm(Model model){
        model.addAttribute("password", new PasswordDTO());
        return viewPrefix + "activate_account";
    }

  @PostMapping("/activate-account")
  public ModelAndView processPasswordChange(
          Principal principal,
          @ModelAttribute("password") @Valid PasswordDTO passwordDTO, BindingResult result) {
      ModelAndView modelAndView = new ModelAndView(viewPrefix + "activate_account", "password", passwordDTO);
      if(result.hasErrors()) return modelAndView;
      if(passwordsAreNotMatching(passwordDTO)){
          modelAndView.addObject("message", "Passwords are not matching");
          return modelAndView;
      }
      userDetailsService.activateUser(principal.getName(), passwordDTO.getPassword());

      return new ModelAndView(viewPrefix + "/login");
  }

  private boolean passwordsAreNotMatching(PasswordDTO passwordDTO){
        return !passwordDTO.getPassword().equals(passwordDTO.getMatchingPassword());
  }

}
