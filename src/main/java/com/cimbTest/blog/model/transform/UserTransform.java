package com.cimbTest.blog.model.transform;

import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserTransform {

  @Named("toUserResponse")
  UserResponse toUserResponse(UserEntity user);

}
