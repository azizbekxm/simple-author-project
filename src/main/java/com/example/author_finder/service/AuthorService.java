package com.example.author_finder.service;

import com.example.author_finder.model.Author;
import com.example.author_finder.model.Work;
import com.example.author_finder.repository.AuthorRepository;
import com.example.author_finder.repository.WorkRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
  private final AuthorRepository authorRepository;
  private final WorkRepository workRepository;
  private final OpenLibraryService openLibraryService;

  public AuthorService(AuthorRepository authorRepository, WorkRepository workRepository,
                       OpenLibraryService openLibraryService) {
    this.authorRepository = authorRepository;
    this.workRepository = workRepository;
    this.openLibraryService = openLibraryService;
  }

  public List<Author> searchAuthors(String name) {
    var authors = authorRepository.findByName(name);
    if (authors.isEmpty()) {
      authors = openLibraryService.searchAuthorsByName(name);
    }
    return authors;
  }

  public List<Work> getAuthorWorksById(String id) {
    var works = workRepository.findByAuthorId(id);
    if (works.isEmpty()) {
      works = openLibraryService.getAuthorWorks(id);
    }
    return works;
  }

}
