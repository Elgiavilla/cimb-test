package com.cimbTest.blog.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cimbTest.blog.config.exception.BusinessException;
import com.cimbTest.blog.model.request.AuthRequest;
import com.cimbTest.blog.model.response.AuthResponse;
import com.cimbTest.blog.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthServiceTest {

  AuthRequest authRequest;

  @Autowired
  AuthService authService;

  @Autowired
  UserRepository userRepository;

  @BeforeAll
  void setUp(){
      authRequest = AuthRequest.builder()
          .email("mail@mail.com")
          .password("123")
          .build();
  }

  @AfterAll
  void cleanUp(){
    userRepository.deleteAll();
  }

  @Test
  @Order(1)
  void testRegister(){
    AuthResponse response = authService.register(authRequest);
    assertNotNull(response);
    assertNotNull(response.getToken());
  }

  @Test
  @Order(2)
  void testRegisterWithThrownException(){
    Exception exception = assertThrows(BusinessException.class, () -> authService.register(authRequest));
    String expectedMessage = "Email is duplicate";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  @Order(3)
  void testLogin(){
    AuthResponse response = authService.login(authRequest);
    assertNotNull(response);
    assertNotNull(response.getToken());
  }

  @Test
  @Order(4)
  void testLoginWithThrownException(){
    authRequest = AuthRequest.builder()
        .email(RandomStringUtils.random(5))
        .password(RandomStringUtils.random(5)).build();
    Exception exception = assertThrows(BusinessException.class, () -> authService.login(authRequest));
    String expectedMessage = "User not found!";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

}
