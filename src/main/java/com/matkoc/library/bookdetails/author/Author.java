package com.matkoc.library.bookdetails.author;

import com.matkoc.library.bookdetails.BookDetails;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@IdClass(AuthorId.class)
@Table(name = "authors")
public class Author {

  @Id
  @Column(name = "first_name")
  private String firstName;

  @Id
  @Column(name = "last_name")
  private String lastName;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "bd_id")
  private BookDetails bookDetails;

  @Override
  public String toString() {
    return firstName + ' ' + lastName;
  }
}
