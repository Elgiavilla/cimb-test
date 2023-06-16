package com.cimbTest.blog.shared.transform;

import com.cimbTest.blog.model.response.PageResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PageTransform {
  @Named("toPageDto")
  @Mapping(target = "currentPage"  , source = "number")
  @Mapping(target = "totalElements"  , source = "totalElements")
  @Mapping(target = "offsetElements"  , source = "offsetElements")
  @Mapping(target = "totalElementsPerPage"  , source = "totalElementsPerPage")
  @Mapping(target = "totalPages"  , source = "totalPages")
  @Mapping(target = "content"  , source = "content")
  @Mapping(target = "isLast", source = "isLast")
  PageResponse toPage(
      Integer number,
      Long totalElements,
      Long offsetElements,
      Integer totalElementsPerPage,
      Integer totalPages,
      List content,
      Boolean isLast);
}