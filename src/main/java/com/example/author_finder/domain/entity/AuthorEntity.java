package com.example.author_finder.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorEntity {
  @Id
  private String id;
  private String name;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<AuthorWorkEntity> works;

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof AuthorEntity that)) {
      return false;
    }
    return Objects.equals(id, that.id) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }
}
