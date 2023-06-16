package com.cimbTest.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cimbTest.blog.config.exception.BusinessException;
import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.model.request.BlogRequest;
import com.cimbTest.blog.model.response.BlogResponse;
import com.cimbTest.blog.model.response.PageResponse;
import com.cimbTest.blog.repository.BlogRepository;
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
public class BlogServiceTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  BlogService blogService;

  @Autowired
  BlogRepository blogRepository;

  UUID userId;

  UUID id;

  BlogRequest blogRequest;

  @BeforeAll
  void setUp(){
    UserEntity entity = new UserEntity();
    entity.setEmail("mail@mail.com");
    entity.setPassword("123");
    UserEntity saved = userRepository.save(entity);
    userId = saved.getId();

    blogRequest = BlogRequest.builder()
        .title("Title")
        .body("Body").build();
  }

  @AfterAll
  void cleanUp(){
    blogRepository.deleteAll();
    userRepository.deleteById(userId);
  }

  @Order(1)
  @Test
  void testInsertNewBlog(){
    BlogResponse response = blogService.insertNewBlog(blogRequest, userId);
    id = response.getId();
    assertEquals(blogRequest.getTitle(), response.getTitle());
    assertEquals(blogRequest.getBody(), response.getBody());
    assertEquals(userId, response.getAuthor().getId());
  }

  @Order(2)
  @Test
  void testInsertNewBlogWithThrowException(){
    Exception exception = assertThrows(BusinessException.class, () -> blogService.insertNewBlog(blogRequest, UUID.randomUUID()));
    String expectedMessage = "User not found!";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Order(3)
  @Test
  void testUpdateBlog(){
    blogRequest = BlogRequest.builder()
        .title("Title Update")
        .body("Body Update")
        .build();
    BlogResponse response = blogService.updateBlog(id, blogRequest, userId);
    assertEquals(blogRequest.getTitle(), response.getTitle());
    assertEquals(blogRequest.getBody(), response.getBody());
  }

  @Order(4)
  @Test
  void testUpdateBlogWithThrowBlogNotFoundException(){
    UUID idTemp = UUID.randomUUID();
    Exception exception = assertThrows(BusinessException.class, () -> blogService.updateBlog(idTemp, blogRequest, userId));
    String expectedMessage = "Blog with id: "+idTemp+" not found!";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Order(5)
  @Test
  void testUpdateBlogWithThrowUserException(){
    Exception exception = assertThrows(BusinessException.class, () -> blogService.updateBlog(id, blogRequest, UUID.randomUUID()));
    String expectedMessage = "Title Update is not yours!";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Order(6)
  @Test
  void testGetBlogById(){
    BlogResponse response = blogService.getBlogById(id);
    assertEquals(response.getId(), id);
  }

  @Order(7)
  @Test
  void testGetBlogByIdThrowNotFound(){
    Exception exception = assertThrows(BusinessException.class, () -> blogService.getBlogById(UUID.randomUUID()));
    String expectedMessage = "Blog not found!";
    String actualMessage = exception.getMessage();
    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Order(8)
  @Test
  void testGetAllBlogs(){
    PageResponse response = blogService.getAllBlogs(1, 10, null, null, null);
    assertNotNull(response);
    assertEquals(1, response.getCurrentPage());
    assertEquals(1, response.getTotalPages());
    assertEquals(1, response.getTotalElements());
    assertEquals(0, response.getOffsetElements());
    assertEquals(1, response.getContent().size());
    assertEquals(true, response.getIsLast());
  }

  @Order(9)
  @Test
  void testGetAllBlogsWithUserId(){
    PageResponse response = blogService.getAllBlogs(1, 10, null, null, userId);
    assertNotNull(response);
    assertEquals(1, response.getCurrentPage());
    assertEquals(1, response.getTotalPages());
    assertEquals(1, response.getTotalElements());
    assertEquals(0, response.getOffsetElements());
    assertEquals(1, response.getContent().size());
    assertEquals(true, response.getIsLast());
  }

  @Order(10)
  @Test
  void testGetAllBlogsWithRandomUserId(){
    PageResponse response = blogService.getAllBlogs(1, 10, null, null, UUID.randomUUID());
    assertNotNull(response);
    assertEquals(1, response.getCurrentPage());
    assertEquals(0, response.getTotalPages());
    assertEquals(0, response.getTotalElements());
    assertEquals(0, response.getOffsetElements());
    assertEquals(0, response.getContent().size());
    assertEquals(true, response.getIsLast());
  }

}
