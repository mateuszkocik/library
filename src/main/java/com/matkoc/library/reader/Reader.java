package com.matkoc.library.reader;

import com.matkoc.library.model.Person;
import com.matkoc.library.rental.Rental;
import com.matkoc.library.reservation.Reservation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "readers")
public class Reader extends Person {

    @OneToMany(cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Rental> rentals;
}
