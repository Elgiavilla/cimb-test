package com.cimbTest.blog.config.security;

import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<UserEntity> user = userRepository.findByEmail(username);
    if(!user.isPresent()){
      throw new UsernameNotFoundException("User not found!");
    }
    return UserDetailsImpl.build(user.get());
  }

  public UserDetails loadUserById(String username) {
    Optional<UserEntity> user = userRepository.findById(UUID.fromString(username));
    if(!user.isPresent()){
      throw new UsernameNotFoundException("User not found!");
    }
    return UserDetailsImpl.build(user.get());
  }
}
