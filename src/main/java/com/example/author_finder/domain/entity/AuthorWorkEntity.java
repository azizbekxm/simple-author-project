package com.example.author_finder.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "works")
@Getter
@Setter
public class AuthorWorkEntity {
  @Id
  private String id;
  private String title;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private AuthorEntity author;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof AuthorWorkEntity that)) {
      return false;
    }
    return Objects.equals(id, that.id) && Objects.equals(title, that.title);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title);
  }
}
