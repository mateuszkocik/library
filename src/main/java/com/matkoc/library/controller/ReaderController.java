package com.matkoc.library.controller;

import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/reader")
public class ReaderController {

  @Autowired
  ReaderService readerService;

  @GetMapping
  public String showReaderPage() {
    return "reader";
  }

  @GetMapping("/profile")
  public String showProfile(Model model, Principal principal) {
    Reader reader = readerService.getReaderByEmail(principal.getName());
    model.addAttribute("reader", reader);
    return "profile";
  }
}
