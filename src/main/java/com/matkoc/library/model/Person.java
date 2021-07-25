package com.matkoc.library.model;

import com.matkoc.library.security.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class Person extends BaseEntity {

  @OneToOne(mappedBy = "username")
  private User user;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "sex")
  private Sex sex;

  @AttributeOverrides({
    @AttributeOverride(name = "telephoneNumber", column = @Column(name = "telephone")),
    @AttributeOverride(name = "email", column = @Column(name = "email"))
  })
  @Embedded
  private ContactInformation contactInformation;
}
