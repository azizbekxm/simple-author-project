package com.example.author_finder.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class OpenLibraryAuthorResponse {
  private int numFound;
  private List<AuthorDoc> docs;

  @Data
  public static class AuthorDoc {
    private String key;
    private String name;
    @JsonProperty("birth_date")
    private String birthDate;
    @JsonProperty("top_work")
    private String topWork;
    @JsonProperty("work_count")
    private Integer workCount;
  }
} 