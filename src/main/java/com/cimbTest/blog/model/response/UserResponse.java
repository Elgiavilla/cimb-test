package com.cimbTest.blog.model.response;

import java.util.UUID;
import lombok.Data;

@Data
public class UserResponse {

  private UUID id;
  private String email;

}
