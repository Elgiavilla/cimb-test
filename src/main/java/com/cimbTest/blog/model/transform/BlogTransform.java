package com.cimbTest.blog.model.transform;

import com.cimbTest.blog.entities.BlogEntity;
import com.cimbTest.blog.entities.UserEntity;
import com.cimbTest.blog.model.request.BlogRequest;
import com.cimbTest.blog.model.response.BlogResponse;
import com.cimbTest.blog.shared.transform.ConvertTransform;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {
    ConvertTransform.class,
    UserTransform.class
})
public interface BlogTransform {

  @Named("toBlogResponse")
  @Mapping(target = "createdAt", source = "entity.createdAt", qualifiedByName = "instantTimeToString")
  @Mapping(target = "updatedAt", source = "entity.createdAt", qualifiedByName = "instantTimeToString")
  @Mapping(target = "author", source = "entity.user", qualifiedByName = "toUserResponse")
  BlogResponse toBlogResponse(BlogEntity entity);

  @Named("toBlogResponseList")
  @IterableMapping(qualifiedByName = "toBlogResponse")
  List<BlogResponse> toBlogResponseList(List<BlogEntity> entities);

  @Named("toBlogInsert")
  default BlogEntity toBlogInsert(BlogRequest request, UserEntity user){
    BlogEntity entity = new BlogEntity();
    entity.setTitle(request.getTitle());
    entity.setBody(request.getBody());
    entity.setUser(user);
    return entity;
  }

  @Named("toBlogUpdate")
  default BlogEntity toBlogUpdate(BlogEntity entity, BlogRequest request){
    entity.setTitle(request.getTitle());
    entity.setBody(request.getBody());
    return entity;
  }
}
