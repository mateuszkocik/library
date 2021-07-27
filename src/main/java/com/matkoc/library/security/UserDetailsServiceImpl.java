package com.matkoc.library.security;

import com.matkoc.library.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.getUserByUsername(username);
    if (user == null) throw new UsernameNotFoundException("User " + username + " not found");
    return new CustomUserDetails(user);
  }

  public User registerNewUser(UserDTO userDTO) {
    User user = new User();
    user.setUsername(userDTO.getEmail());
    user.setPassword(userDTO.getPassword());
    user.setAuthorities(Arrays.asList(new Authority(userDTO.getEmail(), "ROLE_USER")));
    userRepository.save(user);
    return user;
  }
}
