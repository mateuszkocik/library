package com.matkoc.library.book;

import com.matkoc.library.bookdetails.BookDetails;
import com.matkoc.library.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  private BookDetails bookDetails;

  @Column(name = "status")
  private BookStatus bookStatus;
}
