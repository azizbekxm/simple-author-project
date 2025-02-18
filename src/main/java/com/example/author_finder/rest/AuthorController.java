package com.example.author_finder.rest;

import com.example.author_finder.domain.dto.AuthorDto;
import com.example.author_finder.domain.dto.AuthorWorkDto;
import com.example.author_finder.service.AuthorService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping("/search")
  public ResponseEntity<List<AuthorDto>> searchAuthors(@RequestParam String name) {
    return ResponseEntity.ok(authorService.searchAuthors(name));
  }

  @GetMapping("/{authorId}/works")
  public ResponseEntity<List<AuthorWorkDto>> getAuthorWorks(@PathVariable String authorId) {
    return ResponseEntity.ok(authorService.getAuthorWorksById(authorId));
  }
}
