package com.matkoc.library.librarian;

import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.exception.UserAlreadyExistException;
import com.matkoc.library.model.ContactInformation;
import com.matkoc.library.model.Gender;
import com.matkoc.library.security.User;
import com.matkoc.library.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrarianService {
  @Autowired private LibrarianRepository librarianRepository;
  @Autowired private UserDetailsServiceImpl userService;

  public Librarian registerNewLibrarian(UserDTO userDto) throws UserAlreadyExistException {
    User user = userService.registerNewLibrarian(userDto);
    Librarian librarian = new Librarian();
    librarian.setUser(user);
    librarian.setFirstName(userDto.getFirstName());
    librarian.setLastName(userDto.getLastName());
    librarian.setGender(Gender.valueOf(userDto.getGender()));
    ContactInformation contactInformation =
        new ContactInformation(userDto.getTelephoneNumber(), userDto.getEmail());
    librarian.setContactInformation(contactInformation);
    return librarianRepository.save(librarian);
  }
}
