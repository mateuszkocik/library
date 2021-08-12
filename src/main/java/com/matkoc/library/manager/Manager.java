package com.matkoc.library.manager;


import com.matkoc.library.model.Person;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "managers")
public class Manager extends Person{
}
