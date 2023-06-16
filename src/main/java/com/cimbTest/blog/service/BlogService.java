package com.cimbTest.blog.service;

import com.cimbTest.blog.config.exception.BusinessException;
import com.cimbTest.blog.config.security.UserDetailsImpl;
import com.cimbTest.blog.entities.BlogEntity;
import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.model.request.BlogRequest;
import com.cimbTest.blog.model.response.BlogResponse;
import com.cimbTest.blog.model.response.PageResponse;
import com.cimbTest.blog.model.transform.BlogTransform;
import com.cimbTest.blog.repository.BlogRepository;
import com.cimbTest.blog.repository.UserRepository;
import com.cimbTest.blog.service.specification.BlogSpecification;
import com.cimbTest.blog.shared.constant.ResponseCode;
import com.cimbTest.blog.shared.transform.PageTransform;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

  @Autowired
  BlogRepository blogRepository;

  @Autowired
  BlogSpecification blogSpecification;

  @Autowired
  BlogTransform blogTransform;

  @Autowired
  PageTransform pageTransform;

  @Autowired
  UserRepository userRepository;

  public PageResponse<List<BlogResponse>> getAllBlogs(
      Integer page, Integer size, String search, String sort, UUID userId
  ){
    if (page != 0) page -= 1;
    Page<BlogEntity> data = blogRepository.findAll(blogSpecification.toPredicate(search, sort, userId), blogSpecification.pageGenerator(page, size));
    return pageTransform.toPage(
        data.getNumber() + 1,
        data.getTotalElements(),
        data.getPageable().getOffset(),
        data.getPageable().getPageSize(),
        data.getTotalPages(),
        blogTransform.toBlogResponseList(data.getContent()),
        data.isLast()
    );
  }

  public BlogResponse getBlogById(
      UUID id
  ){
    Optional<BlogEntity> blog = blogRepository.findById(id);
    if(!blog.isPresent()){
      throw new BusinessException(ResponseCode.DATA_NOT_EXIST.getCode(), "Blog not found!");
    }

    return blogTransform.toBlogResponse(blog.get());
  }

  public BlogResponse insertNewBlog(
      BlogRequest request,
      UUID userId
  ){
    Optional<UserEntity> user = userRepository.findById(userId);
    if(!user.isPresent()){
      throw new BusinessException(ResponseCode.DATA_NOT_EXIST.getCode(), "User not found!");
    }

    BlogEntity blog = blogRepository.save(
        blogTransform.toBlogInsert(request, user.get())
    );

    return blogTransform.toBlogResponse(blog);
  }

  public BlogResponse updateBlog(
      UUID id,
      BlogRequest blogRequest,
      UUID userId
  ){

    Optional<BlogEntity> blog = blogRepository.findById(id);
    if(!blog.isPresent()){
      throw new BusinessException(ResponseCode.DATA_NOT_EXIST.getCode(), "Blog with id: "+id+" not found!");
    }

    if(!blog.get().getUser().getId().equals(userId)){
      throw new BusinessException(ResponseCode.BAD_REQUEST.getCode(), blog.get().getTitle()+" is not yours!");
    }

    BlogEntity savedBlog = blogRepository.save(
        blogTransform.toBlogUpdate(blog.get(), blogRequest)
    );

    return blogTransform.toBlogResponse(savedBlog);
  }

  public String deleteBlog(UUID id, UUID userId){
    Optional<BlogEntity> blog = blogRepository.findById(id);
    if(!blog.isPresent()){
      throw new BusinessException(ResponseCode.DATA_NOT_EXIST.getCode(), "Blog with id: "+id+" not found!");
    }

    if(!blog.get().getUser().getId().equals(userId)){
      throw new BusinessException(ResponseCode.BAD_REQUEST.getCode(), blog.get().getBody()+" is not yours!");
    }

    blogRepository.delete(blog.get());
    return id+" has been removed";
  }

}
