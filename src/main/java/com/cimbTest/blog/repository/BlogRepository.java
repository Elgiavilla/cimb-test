package com.cimbTest.blog.repository;

import com.cimbTest.blog.entities.BlogEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BlogRepository extends JpaRepository<BlogEntity, UUID>, JpaSpecificationExecutor {



}
