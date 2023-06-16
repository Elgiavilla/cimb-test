package com.cimbTest.blog.controller;

import com.cimbTest.blog.config.documentation.DocumentationConstant;
import com.cimbTest.blog.model.request.AuthRequest;
import com.cimbTest.blog.model.response.AuthResponse;
import com.cimbTest.blog.service.AuthService;
import com.cimbTest.blog.shared.base.BaseResponse;
import com.cimbTest.blog.shared.base.CommonResponse;
import com.cimbTest.blog.shared.constant.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
@Tag(name = DocumentationConstant.AUTH_GROUP_NAME)
public class AuthController {

  @Autowired
  AuthService authService;

  @Operation(summary = "This API for Login")
  @PostMapping("/login")
  public BaseResponse<AuthResponse> login(
      @Valid @RequestBody AuthRequest request
  ){
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        authService.login(request)
    );
  }

  @Operation(summary = "This API for registration")
  @PostMapping("/register")
  public BaseResponse<AuthResponse> register(
      @Valid @RequestBody AuthRequest request
  ){
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        authService.register(request)
    );
  }

}
