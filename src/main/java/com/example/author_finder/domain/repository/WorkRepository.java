package com.example.author_finder.domain.repository;

import com.example.author_finder.domain.entity.AuthorWorkEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<AuthorWorkEntity, String> {
  List<AuthorWorkEntity> findByAuthorId(String id);
}
