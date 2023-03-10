package com.example.townmarket.common.domain.interest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.interest.dto.InterestPagingResponseDto;
import com.example.townmarket.common.domain.interest.repository.InterestRepository;
import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.product.service.ProductServiceImpl;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.dto.PageDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class InterestServiceImplTest {

  @Mock
  InterestRepository interestRepository;

  @Mock
  ProductServiceImpl productService;

  @InjectMocks
  InterestServiceImpl interestService;

  @Test
  @DisplayName("이미 관심상품에 있는 경우 삭제")
  void addInterestAlready() {
    // given
    Product product = Product.builder().build();
    when(productService.findProductById(any())).thenReturn(product);
    when(interestRepository.existsByUserAndProduct(any(), any())).thenReturn(true);

    //when, then
    User user = User.builder().build();
    assertThat(interestService.addInterest(user, 1L)).isTrue();

  }

  @Test
  @DisplayName("현재 관심상품이 아닌 경우 관심상품으로 등록")
  void addInterest() {
    // given
    Product product = Product.builder().build();
    when(productService.findProductById(any())).thenReturn(product);
    when(interestRepository.existsByUserAndProduct(any(), any())).thenReturn(false);

    // when, then
    User user = User.builder().build();
    assertThat(interestService.addInterest(user, 1L)).isFalse();

  }

  @Test
  @DisplayName("내 관심상품 목록 조회 테스트")
  void showMyInterestProducts() {
    // given
    User user = User.builder().id(1L).build();
    PageDto pageDto = PageDto.builder().page(0).size(10).build();

    InterestPagingResponseDto pagingResponseDto = InterestPagingResponseDto.builder().build();
    Page<InterestPagingResponseDto> page = new PageImpl<>(List.of(pagingResponseDto), pageDto.toPageable(), 1);

    when(interestRepository.searchInterestIndexByUser(eq(user), any(Pageable.class))).thenReturn(page);

    // when
    Page<InterestPagingResponseDto> result = interestService.showMyInterestProducts(user, pageDto);

    // then
    assertThat(result).isNotNull();
    List<InterestPagingResponseDto> contents = result.getContent();
    assertThat(contents).hasSize(1);
    assertThat(contents.get(0)).isEqualTo(pagingResponseDto);

  }

}