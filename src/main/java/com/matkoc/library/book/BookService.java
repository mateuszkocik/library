package com.matkoc.library.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  public Book save(Book book){
    return bookRepository.save(book);
  }

  public Book setBookStatus(Book book, BookStatus status) {
    book.setBookStatus(status);
    return bookRepository.save(book);
  }
}
