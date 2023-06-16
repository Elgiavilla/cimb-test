package com.cimbTest.blog.config.exception;

import com.cimbTest.blog.shared.base.BaseResponse;
import com.cimbTest.blog.shared.base.CommonResponse;
import com.cimbTest.blog.shared.base.GlobalBadRequestResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class ExceptionHandling {

  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity businessLogicException(BusinessException ble) {
    log.info("BusinessLogicException {}", ble.getMessage());
    BaseResponse<?> response = CommonResponse.constructResponse(
        ble.getCode(),
        ble.getMessage(),
        null,
        null
    );
    return new ResponseEntity<>(response, HttpStatus.valueOf(ble.getCode()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity exceptionHandling(Exception e){
    log.info("Exception {}", e);
    BaseResponse<?> response = CommonResponse.constructResponse(
        "500",
        e.getMessage(),
        null,
        null
    );
    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex) {
    log.warn("Argument Exception Handler", ex);
    List<GlobalBadRequestResponse> payloadExceptions = new ArrayList<>();
    String message = null;
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      if(message == null){
        message = error.getDefaultMessage();
      }
      payloadExceptions.add(new GlobalBadRequestResponse(
          error.getField(),
          error.getDefaultMessage()));
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      if(message == null){
        message = error.getDefaultMessage();
      }
      payloadExceptions.add(new GlobalBadRequestResponse(error.getObjectName(), error.getDefaultMessage()));
    }

    BaseResponse<?> response = CommonResponse.constructResponse(
        HttpStatus.BAD_REQUEST.toString(),
        "",
        null,
        payloadExceptions
    );
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

}
