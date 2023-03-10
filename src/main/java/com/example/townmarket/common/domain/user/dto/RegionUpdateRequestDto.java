package com.example.townmarket.common.domain.user.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionUpdateRequestDto {

  @NotEmpty(message = "거래할 지역의 읍,면,동 까지 적어주세요.")
  private String region;
}
