package com.matkoc.library.bookdetails.author;

import com.matkoc.library.bookdetails.BookDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorId implements Serializable {
  private String firstName;
  private String lastName;
  private BookDetails bookDetails;
}
