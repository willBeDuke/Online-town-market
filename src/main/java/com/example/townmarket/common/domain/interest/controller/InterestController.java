package com.example.townmarket.common.domain.interest.controller;

import static com.example.townmarket.common.domain.interest.controller.InterestController.INTEREST_URI_API;

import com.example.townmarket.common.domain.interest.dto.InterestPagingResponseDto;
import com.example.townmarket.common.domain.interest.service.InterestService;
import com.example.townmarket.common.dto.PageDto;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping(INTEREST_URI_API)
public class InterestController {

  static final String INTEREST_URI_API = "/interest";
  private final InterestService interestService;
  private final SetHttpHeaders httpHeaders;

  @PostMapping("/{productId}")
  public ResponseEntity<Boolean> addInterest(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long productId) {
    boolean isCancle = interestService.addInterest(userDetails.getUser(), productId);
    return ResponseEntity.ok().body(isCancle);
  }


  @GetMapping("/list")
  @ResponseBody
  public ResponseEntity<Page<InterestPagingResponseDto>> showMyInterestProducts(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @ModelAttribute PageDto pageDto) {
    return ResponseEntity.ok()
        .headers(httpHeaders.setHeaderTypeJson())
        .body(interestService.showMyInterestProducts(userDetails.getUser(), pageDto));
  }

  @GetMapping("/check/{productId}")
  @ResponseBody
  public ResponseEntity<Boolean> checkInterest(
      @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long productId) {
    return ResponseEntity.ok().headers(httpHeaders.setHeaderTypeJson())
        .body(interestService.checkInterest(userDetails.getUser(), productId));
  }
}
