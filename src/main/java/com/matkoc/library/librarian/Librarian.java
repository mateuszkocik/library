package com.matkoc.library.librarian;

import com.matkoc.library.model.Person;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "librarians")
public class Librarian extends Person {
}
