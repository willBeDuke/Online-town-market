package com.example.townmarket.common.domain.trade.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.product.repository.ProductRepository;
import com.example.townmarket.common.domain.trade.dto.CreateTradeDto;
import com.example.townmarket.common.domain.trade.dto.PagingTrade;
import com.example.townmarket.common.domain.trade.entity.Trade;
import com.example.townmarket.common.domain.trade.repository.TradeRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class TradeServiceImplTest {

  @Mock
  private TradeRepository tradeRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private TradeServiceImpl tradeService;

  @Test
  @DisplayName("구메 내역 조회 성공 테스트")
  void getPurchaseList() {

      // given
      User buyer = User.builder().id(1L).username("John").build();
      Pageable pageable = PageRequest.of(0, 10);
      PagingTrade trade = PagingTrade.builder().userName("Sam").productName("headphone").build();
      Page<PagingTrade> tradePage = new PageImpl<>(List.of(trade), pageable, 1);

      when(tradeRepository.findBuyList(pageable, buyer)).thenReturn(tradePage);

      // when
      Page<PagingTrade> result = tradeService.getPurchaseList(buyer, pageable);

      // then
      assertEquals(tradePage, result);

  }

  @Test
  @DisplayName("판매 목록 조회 성공 테스트")
  void getSalesList() {
    // given
    User seller = User.builder().id(1L).build();
    Pageable pageable = PageRequest.of(0, 10);

    // when
    tradeService.getSalesList(seller, pageable);

    // then
    verify(tradeRepository, times(1)).findSaleList(pageable, seller);
  }

  @Test
  @DisplayName("거래 생성 성공 테스트")
  void createTrade() {
    // given
    Long productId = 1L;
    Long buyerId = 2L;
    User seller = mock(User.class);
    Product product = mock(Product.class);
    CreateTradeDto createTradeDto = CreateTradeDto.builder().productId(productId).buyerId(buyerId).build();

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    when(userRepository.findById(buyerId)).thenReturn(Optional.of(User.builder().id(buyerId).build()));

    // when
    tradeService.createTrade(createTradeDto, seller);

    // then
    verify(productRepository, times(1)).findById(productId);
    verify(userRepository, times(1)).findById(buyerId);
    verify(tradeRepository,times(1)).save(any(Trade.class));

  }
}