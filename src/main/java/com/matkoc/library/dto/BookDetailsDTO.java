package com.matkoc.library.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookDetailsDTO {
  private String title;
  private String genre;
  private String publisher;
  private Integer edition;
  private Integer volume;
  private String publicationYear;
  private String publicationPlace;
  private boolean moreThanThreeAuthors;
  private List<AuthorDTO> authors;
  private String authorsS;

  public BookDetailsDTO() {
    this.authors = new ArrayList<>(3);
    for (int i = 0; i < 3; i++) this.authors.add(new AuthorDTO());
  }
}
