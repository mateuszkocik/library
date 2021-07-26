package com.matkoc.library.reader;

import com.matkoc.library.rental.RentalService;
import com.matkoc.library.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderService{

    ReaderRepository readerRepository;
    RentalService rentalService;
    ReservationService reservationService;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, RentalService rentalService, ReservationService reservationService){
        this.readerRepository = readerRepository;
        this.rentalService = rentalService;
        this.reservationService = reservationService;
    }

    public Reader getReaderByUsername(String username){
        return readerRepository.findByEmail(username);
    }
//
//    public List<Rental> getReaderRentals(String username){
//        return readerRepository.findBy()
//    }
}
