package com.example.author_finder.service;

import com.example.author_finder.model.Author;
import com.example.author_finder.model.Work;
import com.example.author_finder.repository.AuthorRepository;
import com.example.author_finder.repository.WorkRepository;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryService {
  private final String BASE_URL = "https://openlibrary.org";

  private final RestTemplate restTemplate;
  private final AuthorRepository authorRepository;
  private final WorkRepository workRepository;

  public OpenLibraryService(RestTemplate restTemplate, AuthorRepository authorRepository,
                            WorkRepository workRepository) {
    this.restTemplate = restTemplate;
    this.authorRepository = authorRepository;
    this.workRepository = workRepository;
  }

  public List<Author> searchAuthorsByName(String name) {
    String url = BASE_URL + "/search/authors.json?q=" + name;
    var response = restTemplate.getForObject(url, JsonNode.class);
    List<Author> authors = new ArrayList<>();

    if (response != null && response.has("docs")) {
      for (JsonNode doc : response.get("docs")) {
        var author = new Author();
        author.setId(doc.get("key").asText().replace("/authors/", ""));
        author.setName(doc.get("name").asText());
        authors.add(author);
      }
      authorRepository.saveAll(authors);
    }
    return authors;
  }

  public List<Work> getAuthorWorks(String authorId) {
    var url = BASE_URL + "/authors/" + authorId + "/works.json";
    var response = restTemplate.getForObject(url, JsonNode.class);
    var works = new ArrayList<Work>();

    if (response != null && response.has("entries")) {
      Author author = authorRepository.findById(authorId).orElse(new Author());
      author.setId(authorId);

      for (JsonNode entry : response.get("entries")) {
        var work = new Work();
        work.setId(entry.get("key").asText().replace("/works/", ""));
        work.setTitle(entry.get("title").asText());
        work.setAuthor(author);
        works.add(work);
      }

      if (!works.isEmpty()) {
        authorRepository.save(author);
        workRepository.saveAll(works);
      }
    }

    return works;
  }
}
