package com.matkoc.library.reservation;

import com.matkoc.library.book.Book;
import com.matkoc.library.model.BaseEntity;
import com.matkoc.library.reader.Reader;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @Column(name = "to_date")
    private LocalDate toDate;
}
