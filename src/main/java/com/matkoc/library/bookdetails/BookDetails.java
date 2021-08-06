package com.matkoc.library.bookdetails;

import com.matkoc.library.book.Book;
import com.matkoc.library.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "bookDetails")
  private List<Book> books;
}
