package com.matkoc.library.librarian;

import com.matkoc.library.model.ContactInformation;
import com.matkoc.library.model.Person;
import com.matkoc.library.model.Sex;
import com.matkoc.library.task.Task;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "librarians")
public class Librarian extends Person {

  @OneToMany(targetEntity = Task.class)
  private List<Task> tasks;

  public Librarian() {}

  public Librarian(
      String firstName,
      String lastName,
      Sex sex,
      ContactInformation contactInformation,
      List<Task> tasks) {
    super(firstName, lastName, sex, contactInformation);
    this.tasks = tasks;
  }

  public List<Task> getTasks() {
    return tasks;
  }

  public void setTasks(List<Task> tasks) {
    this.tasks = tasks;
  }
}
