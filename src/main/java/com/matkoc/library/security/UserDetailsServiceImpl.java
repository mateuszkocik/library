package com.matkoc.library.security;

import com.matkoc.library.dto.UserDTO;
import com.matkoc.library.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  private final String ROLE_READER = "ROLE_READER";
  private final String ROLE_INACTIVE = "ROLE_INACTIVE";

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.getUserByUsername(username);
    if (user == null) throw new UsernameNotFoundException("User " + username + " not found");
    return new CustomUserDetails(user);
  }

  public User registerNewUser(UserDTO userDTO) throws UserAlreadyExistException {
    System.out.println(userDTO.getPassword());
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
    User user = new User();
    user.setUsername(email);
    user.setAuthorities(Set.of(new Authority(email, ROLE_READER)));
    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }
}
