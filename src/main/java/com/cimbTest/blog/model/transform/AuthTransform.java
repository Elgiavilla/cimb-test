package com.cimbTest.blog.model.transform;

import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.model.request.AuthRequest;
import com.cimbTest.blog.model.response.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AuthTransform {

  @Named("toAuthResponse")
  AuthResponse toAuthResponse(String token);

  @Named("toUserEntity")
  default UserEntity toUserEntity(AuthRequest request, String password){
    UserEntity entity = new UserEntity();
    entity.setEmail(request.getEmail());
    entity.setPassword(password);
    return entity;
  }
}
