package com.matkoc.library.book;

import com.matkoc.library.model.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="book")
public class Book extends BaseEntity{

    @Column(name="bd_id")
    private Integer bookDetailsId;

    @Transient
    private BookDetails bookDetails;

    @Column(name="state")
    private char stateChar;

    private State state;
}
