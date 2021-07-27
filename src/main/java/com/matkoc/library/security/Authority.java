package com.matkoc.library.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "authorities")
public class Authority {

  @Id
  @Column(name = "username")
  private String username;

  @Column(name = "authority")
  private String authority;
}
