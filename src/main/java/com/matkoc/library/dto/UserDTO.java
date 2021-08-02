package com.matkoc.library.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Must have abc@def.xyz format")
  private String email;

  private String password;

  @NotBlank(message = "First name is mandatory")
  private String firstName;

  @NotBlank(message = "Last name is mandatory")
  private String lastName;

  @NotBlank(message = "Gender is mandatory")
  private String gender;

  @NotBlank(message = "Telephone number is mandatory")
  @Pattern(regexp = "^\\d{9}$", message = "Must consist only 9 digits")
  private String telephoneNumber;
}
