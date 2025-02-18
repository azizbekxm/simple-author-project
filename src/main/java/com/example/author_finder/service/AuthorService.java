package com.example.author_finder.service;

import com.example.author_finder.domain.dto.AuthorDto;
import com.example.author_finder.domain.dto.AuthorWorkDto;
import com.example.author_finder.domain.mapper.AuthorMapper;
import com.example.author_finder.domain.repository.AuthorRepository;
import com.example.author_finder.domain.repository.WorkRepository;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class AuthorService {
  private final AuthorRepository authorRepository;
  private final WorkRepository workRepository;
  private final OpenLibraryService openLibraryService;
  private final AuthorMapper authorMapper;

  public AuthorService(AuthorRepository authorRepository, WorkRepository workRepository,
                       OpenLibraryService openLibraryService, AuthorMapper authorMapper) {
    this.authorRepository = authorRepository;
    this.workRepository = workRepository;
    this.openLibraryService = openLibraryService;
    this.authorMapper = authorMapper;
  }

  public List<AuthorDto> searchAuthors(String name) {
    log.debug("searchAuthors:: Trying to search authors by name={}", name);
    var authors = authorRepository.findByName(name);
    if (authors.isEmpty()) {
      log.info("searchAuthors:: Author with name '{}' couldn't be found from db, search from OpenLibrary", name);
      authors = openLibraryService.searchAuthorsByName(name);
    }
    return authorMapper.toDtoList(authors);
  }

  public List<AuthorWorkDto> getAuthorWorksById(String id) {
    log.debug("searchAuthors:: Trying to search author works by id={}", id);
    var works = workRepository.findByAuthorId(id);
    if (works.isEmpty()) {
      log.info("searchAuthors:: Author '{}' work couldn't be found from db, search from OpenLibrary", id);
      works = openLibraryService.getAuthorWorks(id);
    }
    return authorMapper.toWorkDtoList(works);
  }
}
