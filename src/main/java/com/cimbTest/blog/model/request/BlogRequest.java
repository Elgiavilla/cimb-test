package com.cimbTest.blog.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogRequest {

  @NotNull
  @NotBlank
  private String title;

  @NotNull
  @NotBlank
  private String body;

}
