package com.cimbTest.blog.controller;

import com.cimbTest.blog.config.documentation.DocumentationConstant;
import com.cimbTest.blog.config.security.UserDetailsImpl;
import com.cimbTest.blog.model.request.BlogRequest;
import com.cimbTest.blog.model.response.BlogResponse;
import com.cimbTest.blog.model.response.PageResponse;
import com.cimbTest.blog.service.BlogService;
import com.cimbTest.blog.shared.base.BaseResponse;
import com.cimbTest.blog.shared.base.CommonResponse;
import com.cimbTest.blog.shared.constant.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/blog")
@SecurityRequirement(name = DocumentationConstant.tagAuthenticationHeader)
@Tag(name = DocumentationConstant.BLOG_GROUP_NAME)
public class BlogController {

  private final static String DEFAULT_PAGE = "1";
  private final static String DEFAULT_SIZE = "10";

  @Autowired
  BlogService blogService;

  @Operation(summary = "Get All Blogs")
  @GetMapping
  public BaseResponse<PageResponse<List<BlogResponse>>> getAllBlogs(
      @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "sort", defaultValue = "createdAt,DESC") String sort
  ){
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        blogService.getAllBlogs(page, size, search, sort, null)
    );
  }

  @Operation(summary = "Get All Blogs By Current Author")
  @GetMapping("/me")
  public BaseResponse<PageResponse<List<BlogResponse>>> getAllBlogsByCurrentAuthor(
      @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "sort", defaultValue = "createdAt,DESC") String sort
  ){
    UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        blogService.getAllBlogs(page, size, search, sort, currentUser.getId())
    );
  }

  @Operation(summary = "Get All Blogs By Current Author")
  @GetMapping("/author/{user_id}")
  public BaseResponse<PageResponse<List<BlogResponse>>> getAllBlogsByAuthor(
      @PathVariable("user_id") UUID userId,
      @RequestParam(value = "page", defaultValue = DEFAULT_PAGE) int page,
      @RequestParam(value = "size", defaultValue = DEFAULT_SIZE) int size,
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "sort", defaultValue = "createdAt,DESC") String sort
  ){
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        blogService.getAllBlogs(page, size, search, sort, userId)
    );
  }

  @Operation(summary = "Create New Blog")
  @PostMapping
  public BaseResponse<BlogResponse> insertNewBlog(
      @Valid @RequestBody BlogRequest request
  ){
    UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        blogService.insertNewBlog(request, currentUser.getId())
    );
  }

  @Operation(summary = "Update Blog")
  @PutMapping("/{id}")
  public BaseResponse<BlogResponse> updateBlog(
      @PathVariable("id") UUID id,
      @Valid @RequestBody BlogRequest request
  ){
    UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        blogService.updateBlog(id, request, currentUser.getId())
    );
  }

  @Operation(summary = "Delete Blog")
  @DeleteMapping("/{id}")
  public BaseResponse<String> deleteBlog(
      @PathVariable("id") UUID id
  ){
    UserDetailsImpl currentUser = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        blogService.deleteBlog(id, currentUser.getId())
    );
  }

  @Operation(summary = "Get By Id")
  @GetMapping("/{id}")
  public BaseResponse<BlogResponse> getById(
      @PathVariable("id") UUID id
  ){
    return CommonResponse.constructResponse(
        ResponseCode.SUCCESS.getCode(),
        ResponseCode.SUCCESS.getMessage(),
        null,
        blogService.getBlogById(id)
    );
  }

}
