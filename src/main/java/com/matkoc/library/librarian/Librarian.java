package com.matkoc.library.librarian;

import com.matkoc.library.model.Person;
import com.matkoc.library.task.Task;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "librarians")
public class Librarian extends Person {

  @OneToMany(cascade = CascadeType.ALL)
  private List<Task> tasks;
}
