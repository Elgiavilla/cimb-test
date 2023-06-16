package com.cimbTest.blog.shared.constant;

public enum ResponseCode {
  SUCCESS("SUCCESS", "SUCCESS")  ,
  SYSTEM_ERROR("INTERNAL_SERVER_ERROR", "Contact our team"),
  DUPLICATE_DATA("CONFLICT", "Duplicate data"),
  DATA_NOT_EXIST("NOT_FOUND", "No data exist"),
  PASSWORD_INVALID("PASSWORD_INVALID", "Password invalid"),
  RUNTIME_ERROR("SYSTEM_ERROR", "Runtime Error"),
  FORBIDDEN_ACCESS("FORBIDDEN_ACCESS", "Token invalid or expired!"),
  BAD_REQUEST("BAD_REQUEST", "Invalid input body!"),
  USE_TOKEN("BAD_REQUEST", "Please input the token"),
  UNAUTHORIZED("UNAUTHORIZED", "Token invalid!")
  ;

  private String code;
  private String message;

  ResponseCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
