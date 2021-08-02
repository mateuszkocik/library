package com.matkoc.library.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class PasswordDTO{

  @NotEmpty(message = "Password must not be empty")
  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
      message =
          "Invalid password format")
  String password;

  @NotEmpty
  String matchingPassword;
}
