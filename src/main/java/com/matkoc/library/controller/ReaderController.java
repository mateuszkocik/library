package com.matkoc.library.controller;

import com.matkoc.library.bookdetails.BookDetails;
import com.matkoc.library.bookdetails.BookDetailsService;
import com.matkoc.library.dto.BookDetailsDTO;
import com.matkoc.library.reader.Reader;
import com.matkoc.library.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/reader")
public class ReaderController {

  @Autowired
  ReaderService readerService;
  @Autowired
  BookDetailsService bookDetailsService;

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

  @GetMapping("/search")
  public String showSearchPage(Model model){
    model.addAttribute("results", new ArrayList<BookDetails>());
    model.addAttribute("bookDetails", new BookDetailsDTO());
    return "search";
  }

  @PostMapping("/search")
  public ModelAndView viewResults(
          @ModelAttribute("results") ArrayList<BookDetails> results,
          @ModelAttribute("bookDetails") BookDetailsDTO bookDetails) {
    ModelAndView modelAndView = new ModelAndView("search");
    results = bookDetailsService.findBookDetails(bookDetails);
    modelAndView.addObject("results", results);
    modelAndView.addObject("bookDetails", bookDetails);

    return modelAndView;
  }
}
