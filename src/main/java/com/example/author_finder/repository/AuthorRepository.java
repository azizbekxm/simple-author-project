package com.example.author_finder.repository;

import com.example.author_finder.model.Author;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
  List<Author> findByName(String name);
}
