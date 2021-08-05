package com.matkoc.library.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  public ArrayList<Book> findBooksWithBookDetails(Long bookDetailsId){
    return bookRepository.findBookByBookDetails_Id(bookDetailsId);
  }

  public Optional<Book> findBookById(Long id){
    return bookRepository.findById(id);
  }

  public Book save(Book book){
    return bookRepository.save(book);
  }
}
