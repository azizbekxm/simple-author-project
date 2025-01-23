package com.example.author_finder.repository;

import com.example.author_finder.model.Work;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, String> {
  List<Work> findByAuthorId(String id);
}
