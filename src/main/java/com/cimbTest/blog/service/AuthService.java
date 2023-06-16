package com.cimbTest.blog.service;

import com.cimbTest.blog.config.exception.BusinessException;
import com.cimbTest.blog.config.security.JwtUtils;
import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.model.request.AuthRequest;
import com.cimbTest.blog.model.response.AuthResponse;
import com.cimbTest.blog.model.transform.AuthTransform;
import com.cimbTest.blog.repository.UserRepository;
import com.cimbTest.blog.shared.constant.ResponseCode;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  AuthTransform authTransform;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  PasswordEncoder passwordEncoder;

  public AuthResponse login(
      AuthRequest request
  ){
    Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());
    if(!user.isPresent()){
      throw new BusinessException(ResponseCode.DATA_NOT_EXIST.getCode(), "User not found!");
    }

    if(!passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
      throw new BusinessException(ResponseCode.PASSWORD_INVALID.getCode(), ResponseCode.PASSWORD_INVALID.getMessage());
    }

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt;
    try{
      jwt = jwtUtils.generateToken(authentication);
    }catch (UnsupportedEncodingException e){
      throw new BusinessException(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    return authTransform.toAuthResponse(jwt);
  }

  public AuthResponse register(
      AuthRequest request
  ){
    Optional<UserEntity> user = userRepository.findByEmail(request.getEmail());
    if(user.isPresent()){
      throw new BusinessException(ResponseCode.BAD_REQUEST.getCode(), "Email is duplicate");
    }

    UserEntity savedUser = userRepository.save(
        authTransform.toUserEntity(request, passwordEncoder.encode(request.getPassword()))
    );

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            savedUser.getEmail(),
            request.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt;
    try{
      jwt = jwtUtils.generateToken(authentication);
    }catch (UnsupportedEncodingException e){
      throw new BusinessException(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    return authTransform.toAuthResponse(jwt);
  }


}
