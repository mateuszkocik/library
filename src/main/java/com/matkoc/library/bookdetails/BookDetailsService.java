package com.matkoc.library.bookdetails;

import com.matkoc.library.bookdetails.author.Author;
import com.matkoc.library.dto.AuthorDTO;
import com.matkoc.library.dto.BookDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    BookDetails firstSave = bookDetailsRepository.save(bookDetails);
    bookDetails.setAuthors(getAuthorsFromDTO(bookDetailsDTO, firstSave));
    bookDetailsRepository.save(firstSave);
  }

  private List<Author> getAuthorsFromDTO(BookDetailsDTO bookDetailsDTO, BookDetails bookDetails) {
    List<AuthorDTO> authors = bookDetailsDTO.getAuthors();
    List<Author> resultAuthors = new ArrayList<>();
    for(AuthorDTO authorDTO : authors) {
      if (stringIsValid(authorDTO.getFirstName()) && stringIsValid(authorDTO.getLastName())) {
        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setBookDetails(bookDetails);
        resultAuthors.add(author);
      }
    }
    return resultAuthors;
  }

  private boolean stringIsValid(String string) {
    return string != null && !string.trim().equals("");
  }

  public void deleteById(Long bookDetailsId) {
    bookDetailsRepository.deleteById(bookDetailsId);
  }
}
