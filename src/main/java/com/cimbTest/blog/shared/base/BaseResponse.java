package com.cimbTest.blog.shared.base;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class BaseResponse<T> implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;
  private String code;
  private String message;
  private List<String> errors;
  private transient T data;
  private Date serverTime;
}
