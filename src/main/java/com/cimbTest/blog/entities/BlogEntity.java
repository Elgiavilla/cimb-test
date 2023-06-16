package com.cimbTest.blog.entities;

import com.cimbTest.blog.shared.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "blogs")
@EntityListeners(AuditingEntityListener.class)
@Data
public class BlogEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JsonBackReference
  @JoinColumn(name = "author", referencedColumnName = "id")
  private UserEntity user;

  @Column(name = "title", columnDefinition = "TEXT")
  private String title;

  @Column(name = "body", columnDefinition = "TEXT")
  private String body;

}
