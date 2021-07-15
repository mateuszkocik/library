package com.matkoc.library.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authorities")
public class Authority {

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "authority")
  private String authority;

  public String getAuthority() {
    return authority;
  }
}
