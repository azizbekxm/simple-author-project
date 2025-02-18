package com.example.author_finder.service;

import com.example.author_finder.domain.dto.OpenLibraryAuthorResponse;
import com.example.author_finder.domain.dto.OpenLibraryWorksResponse;
import com.example.author_finder.domain.entity.AuthorEntity;
import com.example.author_finder.domain.entity.AuthorWorkEntity;
import com.example.author_finder.domain.mapper.AuthorMapper;
import com.example.author_finder.domain.repository.AuthorRepository;
import com.example.author_finder.domain.repository.WorkRepository;
import com.example.author_finder.util.OpenLibraryIdGenerator;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
public class OpenLibraryService {
  private final String BASE_URL = "https://openlibrary.org";

  private final RestTemplate restTemplate;
  private final AuthorRepository authorRepository;
  private final WorkRepository workRepository;
  private final AuthorMapper authorMapper;

  public OpenLibraryService(AuthorRepository authorRepository, WorkRepository workRepository,
                            AuthorMapper authorMapper) {
    this.authorMapper = authorMapper;
    this.restTemplate = new RestTemplate();
    this.authorRepository = authorRepository;
    this.workRepository = workRepository;
  }

  public List<AuthorEntity> searchAuthorsByName(String name) {
    log.debug("searchAuthorsByName:: Searching for authors with name={}", name);
    String url = BASE_URL + "/search/authors.json?q=" + name;
    var response = restTemplate.getForObject(url, OpenLibraryAuthorResponse.class);
    List<AuthorEntity> authors = new ArrayList<>();

    if (response != null && response.getDocs() != null) {
      log.info("searchAuthorsByName:: Found {} authors for name={}", response.getNumFound(), name);
      authors = response.getDocs().stream()
        .map(doc -> {
          String authorId = OpenLibraryIdGenerator.extractId(doc.getKey(), false);
          return authorRepository.findById(authorId).orElseGet(() -> authorMapper.toEntity(doc));
        })
        .toList();
      authorRepository.saveAll(authors);
      log.debug("searchAuthorsByName:: Saved {} authors to database", authors.size());
    }
    return authors;
  }

  public List<AuthorWorkEntity> getAuthorWorks(String authorId) {
    log.debug("getAuthorWorks:: Fetching works for author with id={}", authorId);
    var url = BASE_URL + "/authors/" + authorId + "/works.json";
    var response = restTemplate.getForObject(url, OpenLibraryWorksResponse.class);
    List<AuthorWorkEntity> works = new ArrayList<>();

    if (response != null && response.getEntries() != null) {
      log.info("getAuthorWorks:: Found {} works for author with id={}", response.getSize(), authorId);
      AuthorEntity author = authorRepository.findById(authorId)
        .orElseGet(() -> {
          AuthorEntity newAuthor = new AuthorEntity();
          newAuthor.setId(authorId);
          return newAuthor;
        });

      works = response.getEntries().stream()
        .map(entry -> {
          String workId = OpenLibraryIdGenerator.extractId(entry.getKey(), true);
          return workRepository.findById(workId)
            .orElseGet(() -> authorMapper.toEntity(entry, author));
        })
        .toList();

      if (!works.isEmpty()) {
        authorRepository.save(author);
        workRepository.saveAll(works);
        log.info("getAuthorWorks:: Saved {} works to database for author={}", works.size(), authorId);
      }
    }
    return works;
  }
}
