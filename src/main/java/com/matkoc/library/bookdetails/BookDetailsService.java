package com.matkoc.library.bookdetails;

import com.matkoc.library.dto.BookDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookDetailsService{
    private final BookDetailsRepository bookDetailsRepository;

    @Autowired
    public BookDetailsService(BookDetailsRepository bookDetailsRepository){
        this.bookDetailsRepository = bookDetailsRepository;
    }

    public void addBookDetails(BookDetailsDTO bookDetailsDTO){
        BookDetails bookDetails = new BookDetails();
        bookDetails.setTitle(bookDetailsDTO.getTitle());
        bookDetails.setGenre(bookDetailsDTO.getGenre());
        bookDetails.setPublisher(bookDetailsDTO.getPublisher());
        bookDetailsRepository.save(bookDetails);
    }
}
