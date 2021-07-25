package com.matkoc.library.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class ContactInformation {

  private String telephoneNumber;
  private String email;
}
