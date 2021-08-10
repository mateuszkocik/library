package com.matkoc.library.bookdetails;

import com.matkoc.library.dto.BookDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BookDetailsService {
  private final BookDetailsRepository bookDetailsRepository;

  @Autowired
  public BookDetailsService(BookDetailsRepository bookDetailsRepository) {
    this.bookDetailsRepository = bookDetailsRepository;
  }

  public BookDetails findById(Long id) {
    return bookDetailsRepository.findById(id).get();
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

  public void deleteById(Long bookDetailsId) {
    bookDetailsRepository.deleteById(bookDetailsId);
  }
}
