package com.matkoc.library.reservation;

import org.springframework.beans.factory.annotation.Autowired;

public class ReservationService {

  public ReservationRepository reservationRepository;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }
}
