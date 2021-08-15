package com.matkoc.library.bookdetails.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

  @Autowired AuthorRepository authorRepository;

}
