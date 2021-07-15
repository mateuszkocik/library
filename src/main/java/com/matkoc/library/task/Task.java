package com.matkoc.library.task;

import com.matkoc.library.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

  @Column(name = "status")
  private TaskStatus status;

  @Column(name = "description")
  private String description;

  @Column(name = "deadline")
  private LocalDate deadline;
}
