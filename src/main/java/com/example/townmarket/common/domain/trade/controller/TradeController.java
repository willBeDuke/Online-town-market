package com.example.townmarket.common.domain.trade.controller;

import static com.example.townmarket.common.util.HttpResponseEntity.RESPONSE_OK;

import com.example.townmarket.common.domain.trade.dto.CreateTradeDto;
import com.example.townmarket.common.domain.trade.dto.PagingTrade;
import com.example.townmarket.common.domain.trade.service.TradeService;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trade")
public class TradeController {

  private final TradeService tradeService;

  private final SetHttpHeaders setHttpHeaders;

  // 본인 판매/구매 리스트
  // 타인 판매 리스트

  @GetMapping("/purchase")
  public ResponseEntity<Page<PagingTrade>> getPurchaseList(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return ResponseEntity.ok().headers(setHttpHeaders.setHeaderTypeJson())
        .body(tradeService.getPurchaseList(userDetails.getUser(), pageable));
  }

  @GetMapping("/sales")
  public ResponseEntity<Page<PagingTrade>> getSalesList(
      @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return ResponseEntity.ok().headers(setHttpHeaders.setHeaderTypeJson())
        .body(tradeService.getSalesList(userDetails.getUser(), pageable));
  }

//  @GetMapping("/sales/{userId}")
//  public ResponseEntity<Page<PagingTrade>> getSalesListOfOther(
//      @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
//      @PathVariable Long userId) {
//    return ResponseEntity.ok().headers(setHttpHeaders.setHeaderTypeJson())
//        .body(tradeService.getSalesListOfOther(userId, pageable));
//  }

  @PostMapping("/create")
  public ResponseEntity<StatusResponse> createTrade(@RequestBody CreateTradeDto createTrade,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    tradeService.createTrade(createTrade, userDetails.getUser());
    return RESPONSE_OK;
  }


}
