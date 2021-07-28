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

  public Reader getReaderByUsername(String username) {
    return readerRepository.findByEmail(username);
  }

    public Reader registerNewReader(UserDTO userDto) throws UserAlreadyExistException{
      User user = userService.registerNewUser(userDto);
      Reader reader = new Reader();
      reader.setUser(user);
      reader.setFirstName(userDto.getFirstName());
      reader.setLastName(userDto.getLastName());
      reader.setGender(Gender.valueOf(userDto.getGender()));
      ContactInformation contactInformation = new ContactInformation(userDto.getTelephoneNumber(), userDto.getEmail());
      reader.setContactInformation(contactInformation);
      reader.setReservations(Collections.emptyList());
      reader.setRentals(Collections.emptyList());
      return readerRepository.save(reader);
    }
}
