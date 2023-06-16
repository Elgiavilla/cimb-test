package com.cimbTest.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cimbTest.blog.config.exception.BusinessException;
import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.model.response.UserResponse;
import com.cimbTest.blog.repository.UserRepository;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
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
public class UserServiceTest {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  UUID userId;

  @BeforeAll
  void setUp(){
    UserEntity entity = new UserEntity();
    entity.setEmail("mail@mail.com");
    entity.setPassword("123");
    UserEntity saved = userRepository.save(entity);
    userId = saved.getId();
  }

  @AfterAll
  void cleanUp(){
    userRepository.deleteById(userId);
  }

  @Order(1)
  @Test
  void testProfile(){
    UserResponse response = userService.profile(userId);
    assertEquals(response.getEmail(), "mail@mail.com");
    assertEquals(userId, response.getId());
  }

  @Order(2)
  @Test
  void testProfileWithException(){
    Exception exception = assertThrows(BusinessException.class, () -> userService.profile(UUID.randomUUID()));
    String expectedMessage = "User not found!";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

}
