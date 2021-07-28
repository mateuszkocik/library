package com.matkoc.library.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RentalDTO {

  @NotNull(message = "Book ID is mandatory")
  @Digits(integer = 17 ,fraction = 0, message = "Book ID must be integer value")
  @Range(message = "Book ID must be positive value")
  Long bookId;

  @NotNull(message = "Email is mandatory")
  @Email(message = "Value have not a proper email format")
  String userEmail;
}
