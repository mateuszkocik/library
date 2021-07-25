package com.matkoc.library.controller;

import com.matkoc.library.reader.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController{

    @Autowired
    ReaderService readerService;

}
