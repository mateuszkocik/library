package com.matkoc.library.reader;

import com.matkoc.library.model.Person;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "readers")
public class Reader extends Person { }
