package com.matkoc.library.reader;

import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.exception.UserAlreadyExistException;
import com.matkoc.library.model.ContactInformation;
import com.matkoc.library.model.Gender;
import com.matkoc.library.rental.RentalService;
import com.matkoc.library.reservation.ReservationService;
import com.matkoc.library.security.User;
import com.matkoc.library.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReaderService {

  ReaderRepository readerRepository;
  RentalService rentalService;
  ReservationService reservationService;
  UserDetailsServiceImpl userService;

  @Autowired
  public ReaderService(
      ReaderRepository readerRepository,
      RentalService rentalService,
      ReservationService reservationService,
      UserDetailsServiceImpl userService) {
    this.readerRepository = readerRepository;
    this.rentalService = rentalService;
    this.reservationService = reservationService;
    this.userService = userService;
  }

  public Reader getReaderByEmail(String email) {
    return readerRepository.findByEmail(email);
  }

  public Reader getReaderById(Long id) {
    return readerRepository.findById(id).get();
  }

  public Reader registerNewReader(UserDTO userDto) throws UserAlreadyExistException {
    User user = userService.registerNewUser(userDto);
    Reader reader = new Reader();
    reader.setUser(user);
    reader.setFirstName(userDto.getFirstName());
    reader.setLastName(userDto.getLastName());
    reader.setGender(Gender.valueOf(userDto.getGender()));
    ContactInformation contactInformation =
        new ContactInformation(userDto.getTelephoneNumber(), userDto.getEmail());
    reader.setContactInformation(contactInformation);
    reader.setReservations(Collections.emptyList());
    reader.setRentals(Collections.emptyList());
    return readerRepository.save(reader);
  }

  public boolean readerHasLessThan5Reservations(Reader reader) {
    if (reader.getReservations() == null) return false;
    return reader.getReservations().size() < 5;
  }

  public Reader save(Reader reader) {
    return readerRepository.save(reader);
  }

  public List<Reader> getAllReaders() {
    return readerRepository.findAll();
  }
}
