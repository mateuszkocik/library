package com.matkoc.library.model;

import javax.persistence.Embeddable;

@Embeddable
public class ContactInformation {
  private String telephoneNumber;
  private String email;

  public ContactInformation() {}

  public ContactInformation(String telephoneNumber, String email) {
    this.telephoneNumber = telephoneNumber;
    this.email = email;
  }

  public String getTelephoneNumber() {
    return telephoneNumber;
  }

  public String getEmail() {
    return email;
  }

    public void setTelephoneNumber(String telephoneNumber){
        this.telephoneNumber = telephoneNumber;
    }

    public void setEmail(String email){
        this.email = email;
    }
}
