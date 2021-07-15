package com.matkoc.library.security;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "username")
  String username;

  @Column(name = "password")
  String password;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "username")
  List<Authority> authorities;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public List<Authority> getAuthorities() {
    return authorities;
  }
}
