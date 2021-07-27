package com.matkoc.library.security;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

  @Id
  @Column(name = "username")
  String username;

  @Column(name = "password")
  String password;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "username")
  List<Authority> authorities;
}
