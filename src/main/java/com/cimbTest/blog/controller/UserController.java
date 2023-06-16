package com.cimbTest.blog.controller;

import com.cimbTest.blog.config.documentation.DocumentationConstant;
import com.cimbTest.blog.config.security.UserDetailsImpl;
import com.cimbTest.blog.model.response.UserResponse;
import com.cimbTest.blog.service.UserService;
import com.cimbTest.blog.shared.base.BaseResponse;
import com.cimbTest.blog.shared.base.CommonResponse;
import com.cimbTest.blog.shared.constant.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
@SecurityRequirement(name = DocumentationConstant.tagAuthenticationHeader)
@Tag(name = DocumentationConstant.AUTH_GROUP_NAME)
public class UserController {

  @Autowired
  UserService userService;

  @Operation(summary = "Get Profile")
  @GetMapping("/profile")
  public BaseResponse<UserResponse> getProfile(){
    UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        userService.profile(currentUser.getId())
    );
  }

}
