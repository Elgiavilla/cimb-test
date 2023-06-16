package com.cimbTest.blog.shared.base;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import java.util.Locale;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class BaseSpecification {
  public Pageable pageGenerator(int page, int size) {
    return PageRequest.of(page, size);
  }

  public Order getSortDirection(String sort, CriteriaBuilder builder, Root root) {
    String[] splSort = sort.split(",");
    String direction = "DESC";
    if (splSort[1] != null) {
      direction = splSort[1];
    }
    if (direction.toUpperCase(Locale.ROOT).equals("DESC")) {
      return builder.desc(root.get(splSort[0]));
    } else {
      return builder.asc(root.get(splSort[0]));
    }
  }
}
