package com.example.author_finder.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class OpenLibraryWorksResponse {
  private int size;
  private List<WorkEntry> entries;

  @Data
  public static class WorkEntry {
    private String key;
    private String title;
    @JsonProperty("first_publish_date")
    private String firstPublishDate;
    @JsonProperty("cover_i")
    private Integer coverId;
    private String description;
  }
} 