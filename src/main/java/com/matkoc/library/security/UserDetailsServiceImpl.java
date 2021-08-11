package com.matkoc.library.security;

import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;
  @Autowired private BCryptPasswordEncoder passwordEncoder;
  private final String ROLE_READER = "ROLE_READER";
  private final String ROLE_INACTIVE = "ROLE_INACTIVE";
  private final String ROLE_INACTIVE_LIB = "ROLE_INACTIVE_LIB";
  private final String ROLE_LIBRARIAN = "ROLE_LIBRARIAN";

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.getUserByUsername(username);
    if (user == null) throw new UsernameNotFoundException("User " + username + " not found");
    return new CustomUserDetails(user);
  }

  public User registerNewUser(UserDTO userDTO) throws UserAlreadyExistException {
    if (userExistsInDatabase(userDTO.getEmail()))
      throw new UserAlreadyExistException(userDTO.getEmail());
    User user = new User();
    user.setUsername(userDTO.getEmail());
    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    user.setAuthorities(Set.of(new Authority(userDTO.getEmail(), ROLE_INACTIVE)));

    return userRepository.save(user);
  }

  private boolean userExistsInDatabase(String email) {
    return userRepository.getUserByUsername(email) != null;
  }

  public void activateUser(String email, String newPassword) {
    User deletedUser = userRepository.getUserByUsername(email);
    User newUser = new User();
    String role = getUserRoleAfterActivation(deletedUser);
    newUser.setUsername(email);
    newUser.setAuthorities(Set.of(new Authority(email, role)));
    newUser.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(newUser);
  }

  private String getUserRoleAfterActivation(User user) {
    Set<Authority> authorities = user.getAuthorities();
    for (Authority authority : authorities) {
      String authorityString = authority.getAuthority();
      if (authorityString.equals("ROLE_INACTIVE")) {
        return ROLE_READER;
      } else {
        return ROLE_LIBRARIAN;
      }
    }
    return null;
  }

}
