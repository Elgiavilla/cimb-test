package com.cimbTest.blog.config.security;

import com.cimbTest.blog.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

  private UUID id;
  private String email;

  @JsonIgnore
  private String password;

  public UserDetailsImpl(UUID id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  public static UserDetailsImpl build(UserEntity user) {

    return new UserDetailsImpl(
        user.getId(),
        user.getEmail(),
        user.getPassword());
  }

  public UUID getId(){
    return id;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
