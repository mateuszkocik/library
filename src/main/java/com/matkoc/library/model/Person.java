package com.matkoc.library.model;

import javax.persistence.*;

@MappedSuperclass
public class Person extends BaseEntity {

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "sex")
  private Sex sex;

  @AttributeOverrides({
    @AttributeOverride(name = "telephoneNumber", column = @Column(name = "telephone_number"))
    //          @AttributeOverride(name="email",
    //                  column=@Column(name="email"))
  })
  @Embedded
  private ContactInformation contactInformation;

  public Person() {}

  public Person(String firstName, String lastName, Sex sex, ContactInformation contactInformation) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.sex = sex;
    this.contactInformation = contactInformation;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public ContactInformation getContactInformation() {
    return contactInformation;
  }
}
