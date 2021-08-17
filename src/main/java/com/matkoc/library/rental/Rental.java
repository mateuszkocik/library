package com.matkoc.library.rental;

import com.matkoc.library.book.Book;
import com.matkoc.library.model.BaseEntity;
import com.matkoc.library.reader.Reader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "from_date")
    LocalDate fromDate;
    @Column(name = "to_date")
    LocalDate toDate;
    @Column(name = "return_date")
    LocalDate returnDate;
    @Column(name = "finished")
    boolean finished;

    public boolean isLate() {
        return LocalDate.now().isAfter(toDate);
    }

    public int getStatus() {
        if (finished)
            return 2;
        else if (isLate())
            return 1;
        else
            return 3;
    }
}
