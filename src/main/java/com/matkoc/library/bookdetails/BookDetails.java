package com.matkoc.library.bookdetails;

import com.matkoc.library.book.Book;
import com.matkoc.library.model.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "book_details")
public class BookDetails extends BaseEntity {

  @Column(name = "title")
  private String title;

  @Column(name = "genre")
  private String genre;

  @Column(name = "publisher")
  private String publisher;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Book> books;
}
