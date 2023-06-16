package com.cimbTest.blog.service.specification;

import com.cimbTest.blog.entities.BlogEntity;
import com.cimbTest.blog.shared.base.BaseSpecification;
import jakarta.persistence.criteria.Predicate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class BlogSpecification extends BaseSpecification {
  public Specification<BlogEntity> toPredicate(String search, String sort, UUID userId) {

    return ((root, query, builder) -> {
      List<Predicate> predicates = new ArrayList<>();
      Predicate predicate;
      if (search != null && !search.isEmpty()) {
        String searchLike = String.format("%%%s%%", search.toLowerCase());
        predicate = builder.like(builder.lower(root.get("title")), searchLike);
        predicates.add(predicate);
      }

      if(userId != null){
        predicate = builder.equal(root.get("user").get("id"), userId);
        predicates.add(predicate);
      }

      query.where(builder.and(predicates.toArray(new Predicate[] {})));
      if (null != sort && !sort.isEmpty()) {
        query.orderBy(getSortDirection(sort, builder, root));
      }
      return query.getRestriction();
    });
  }
}
