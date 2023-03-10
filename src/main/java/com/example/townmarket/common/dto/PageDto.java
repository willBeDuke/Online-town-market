package com.example.townmarket.common.dto;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
public class PageDto {

  int page;

  int size;

  String sortBy;

  boolean isAsc; // boolean은 is를 뺴고 인식

  String keyword;

  public Pageable toPageable() {
    if (Objects.isNull(sortBy)) {
      return PageRequest.of(page, size,
          Sort.by("createdAt").descending()); //sortBy 입력안하면 작성일자 기준으로 내림차순
    } else {
      return PageRequest.of(page, size, Sort.by(sortBy).descending());
    }
  }

}
