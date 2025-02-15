package com.example.author_finder.domain.repository;

import com.example.author_finder.domain.entity.AuthorEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, String> {
  List<AuthorEntity> findByName(String name);
}
