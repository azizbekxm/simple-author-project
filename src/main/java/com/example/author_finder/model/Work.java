package com.example.author_finder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="works")
public class Work {
  @Id
  private String id;
  private String title;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private Author author;

  public String getId() {
    return id;
  }

  public Work setId(String id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public Work setTitle(String title) {
    this.title = title;
    return this;
  }

  public Author getAuthor() {
    return author;
  }

  public Work setAuthor(Author author) {
    this.author = author;
    return this;
  }
}
