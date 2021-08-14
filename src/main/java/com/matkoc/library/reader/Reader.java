package com.matkoc.library.reader;

import com.matkoc.library.bookdetails.BookDetails;
import com.matkoc.library.model.Person;
import com.matkoc.library.rental.Rental;
import com.matkoc.library.reservation.Reservation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "readers")
public class Reader extends Person {

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reader")
    private List<Reservation> reservations;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "reader")
    private List<Rental> rentals;

    public int getReaderRentalsSize() {
        List<Rental> r = getRentals();
        return r == null ? 0 : r.size();
    }
}
