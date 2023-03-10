package com.example.townmarket.common.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DuplicateCheckResponseDto {

  boolean duplicate;

  public DuplicateCheckResponseDto(boolean duplicate) {
    this.duplicate = duplicate;
  }
}
