package com.matkoc.library.bookdetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BookDetailsRepository extends JpaRepository<BookDetails, Long> {

  ArrayList<BookDetails> findBookDetailsByTitleContainingAndGenreContainingAndPublisherContaining(
      String title, String genre, String publisher);
}
