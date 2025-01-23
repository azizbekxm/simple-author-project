package com.example.author_finder.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name="authors")
public class Author {
  @Id
  private String id;
  private String name;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  private List<Work> works;

  public String getId() {
    return id;
  }

  public Author setId(String id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Author setName(String name) {
    this.name = name;
    return this;
  }
}
