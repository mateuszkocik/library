package com.matkoc.library.bookdetails;

import com.matkoc.library.book.Book;
import com.matkoc.library.bookdetails.author.Author;
import com.matkoc.library.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "book_details")
public class BookDetails extends BaseEntity {

  @Column(name = "title")
  private String title;

  @Column(name = "genre")
  private String genre;

  @Column(name = "publisher")
  private String publisher;

  @Column(name = "edition")
  private Integer edition;

  @Column(name = "volume")
  private Integer volume;

  @Column(name = "publication_year")
  private String publicationYear;

  @Column(name = "publication_place")
  private String publicationPlace;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bookDetails")
  private Collection<Author> authors;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bookDetails")
  private Collection<Book> books;

  public String getAuthorsAsString() {
    Collection<Author> authors = getAuthors();
    if(authors == null || authors.isEmpty())
      return "Unknown";
    StringBuilder result = new StringBuilder();
    for(Author author : authors) {
      result.append(author.toString() + ", ");
    }
    return result.substring(0, result.length() - 2);
  }
}
