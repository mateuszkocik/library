package com.matkoc.library.rental;

import com.matkoc.library.book.Book;
import com.matkoc.library.model.BaseEntity;
import com.matkoc.library.reader.Reader;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "rentals")
public class Rental extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    Reader reader;

    LocalDate fromDate;
    LocalDate toDate;
    LocalDate returnDate;
    BigDecimal fee;
    boolean finished;
}
