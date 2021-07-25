package com.matkoc.library.task;

import com.matkoc.library.librarian.Librarian;
import com.matkoc.library.model.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "librarian_id")
  private Librarian librarian;

  @Column(name = "status")
  private TaskStatus status;

  @Column(name = "description")
  private String description;

  @Column(name = "deadline")
  private LocalDate deadline;
}
