package com.example.author_finder.domain.mapper;

import com.example.author_finder.domain.dto.AuthorDto;
import com.example.author_finder.domain.dto.AuthorWorkDto;
import com.example.author_finder.domain.dto.OpenLibraryAuthorResponse.AuthorDoc;
import com.example.author_finder.domain.dto.OpenLibraryWorksResponse.WorkEntry;
import com.example.author_finder.domain.entity.AuthorEntity;
import com.example.author_finder.domain.entity.AuthorWorkEntity;
import com.example.author_finder.util.OpenLibraryIdGenerator;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

  @Mapping(target = "id", source = "key", qualifiedByName = "extractAuthorId")
  AuthorEntity toEntity(AuthorDoc authorDoc);

  @Mapping(target = "id", source = "workEntry.key", qualifiedByName = "extractWorkId")
  AuthorWorkEntity toEntity(WorkEntry workEntry, AuthorEntity author);

  AuthorDto toDto(AuthorEntity author);

  AuthorWorkDto toWorkDto(AuthorWorkEntity work);

  List<AuthorDto> toDtoList(List<AuthorEntity> authors);

  List<AuthorWorkDto> toWorkDtoList(List<AuthorWorkEntity> works);

  @Named("extractAuthorId")
  default String extractAuthorId(String key) {
    return OpenLibraryIdGenerator.extractId(key, false);
  }

  @Named("extractWorkId")
  default String extractWorkId(String key) {
    return OpenLibraryIdGenerator.extractId(key, true);
  }
} 