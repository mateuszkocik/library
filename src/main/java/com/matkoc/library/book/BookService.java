package com.matkoc.library.book;

import com.matkoc.library.bookdetails.BookDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

  public Optional<Book> getBookById(Long id) {
    return bookRepository.findById(id);
  }

  public Book addNewBookToBookDetails(BookDetails bookDetails) {
    Book book = new Book();
    book.setBookDetails(bookDetails);
    book.setBookStatus(BookStatus.AVAILABLE);
    return bookRepository.save(book);
  }

  public void deleteBook(Long bookId){
    bookRepository.deleteById(bookId);
  }
}
