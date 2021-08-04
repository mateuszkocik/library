package com.matkoc.library.bookdetails;

import com.matkoc.library.dto.BookDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BookDetailsService {
  private final BookDetailsRepository bookDetailsRepository;

  @Autowired
  public BookDetailsService(BookDetailsRepository bookDetailsRepository) {
    this.bookDetailsRepository = bookDetailsRepository;
  }

  public ArrayList<BookDetails> findBookDetails(BookDetailsDTO bookDetails) {
    return bookDetailsRepository
        .findBookDetailsByTitleContainingAndGenreContainingAndPublisherContaining(
            bookDetails.getTitle(), bookDetails.getGenre(), bookDetails.getPublisher());
  }

  public void addBookDetails(BookDetailsDTO bookDetailsDTO) {
    BookDetails bookDetails = new BookDetails();
    bookDetails.setTitle(bookDetailsDTO.getTitle());
    bookDetails.setGenre(bookDetailsDTO.getGenre());
    bookDetails.setPublisher(bookDetailsDTO.getPublisher());
    bookDetailsRepository.save(bookDetails);
  }
}
