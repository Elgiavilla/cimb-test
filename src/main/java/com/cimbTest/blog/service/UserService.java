package com.cimbTest.blog.service;

import com.cimbTest.blog.config.exception.BusinessException;
import com.cimbTest.blog.config.security.UserDetailsImpl;
import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.model.response.UserResponse;
import com.cimbTest.blog.model.transform.UserTransform;
import com.cimbTest.blog.repository.UserRepository;
import com.cimbTest.blog.shared.constant.ResponseCode;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserTransform userTransform;

  public UserResponse profile(UUID userId){
    Optional<UserEntity> user = userRepository.findById(userId);
    if(!user.isPresent()){
      throw new BusinessException(ResponseCode.DATA_NOT_EXIST.getCode(), "User not found!");
    }

    return userTransform.toUserResponse(user.get());
  }

}
