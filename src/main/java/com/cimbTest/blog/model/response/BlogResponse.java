package com.cimbTest.blog.model.response;

import com.cimbTest.blog.shared.base.AuditBaseResponse;
import java.util.UUID;
import lombok.Data;

@Data
public class BlogResponse extends AuditBaseResponse {

  private UUID id;
  private String title;
  private String body;
  private UserResponse author;

}
