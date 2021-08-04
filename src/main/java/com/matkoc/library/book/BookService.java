package com.matkoc.library.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookService {

  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }


  public ArrayList<Book> findBooksWithBookDetails(Long bookDetailsId){
    return bookRepository.findBookByBookDetails_Id(bookDetailsId);
  }

}
