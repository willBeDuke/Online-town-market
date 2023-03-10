package com.example.townmarket.common.domain.product.service;

import static com.example.townmarket.fixture.ProductFixture.PRODUCT;
import static com.example.townmarket.fixture.ProductFixture.PRODUCT_ID;
import static com.example.townmarket.fixture.ProductFixture.PRODUCT_RESPONSE_DTO;
import static com.example.townmarket.fixture.UserFixture.PROFILE_RESPONSE_DTO;
import static com.example.townmarket.fixture.UserFixture.USER1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.townmarket.common.domain.product.dto.PagingProductResponse;
import com.example.townmarket.common.domain.product.dto.ProductRequestDto;
import com.example.townmarket.common.domain.product.dto.ProductResponseDto;
import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.product.entity.Product.ProductCategory;
import com.example.townmarket.common.domain.product.entity.Product.ProductEnum;
import com.example.townmarket.common.domain.product.entity.Product.ProductStatus;
import com.example.townmarket.common.domain.product.repository.ProductRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.dto.PageDto;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

  @Mock
  ProductRepository productRepository;

  @InjectMocks
  ProductServiceImpl productService;

  @Test
  @DisplayName("상품 등록 성공")
  void addProduct() {
    // given
    ProductRequestDto productRequestDto = mock(ProductRequestDto.class);
    User user = mock(User.class);

    // when
    productService.addProduct(user, productRequestDto);

    // then
    verify(productRepository, times(1)).save(isA(Product.class));
  }

  @Test
  @DisplayName("상품 단건 조회 성공")
  void getProduct() {

    // given
    when(productRepository.getProductAndSellerProfileByProductIdAndCountView(PRODUCT_ID)).thenReturn(
        PRODUCT);

    // when
    ProductResponseDto actualProduct = productService.getProduct(PRODUCT_ID);

    // then
    assertThat(actualProduct.getProductName()).isEqualTo(PRODUCT_RESPONSE_DTO.getProductName());
    assertThat(actualProduct.getProductId()).isEqualTo(PRODUCT_RESPONSE_DTO.getProductId());
  }

  @Test
  @DisplayName("상품 단건 조회 실패 - 신고 누적된 상품")
  void failsGetProduct() {

    Product product = Product.builder().build(); // NPE 방지
    when(productRepository.getProductAndSellerProfileByProductIdAndCountView(PRODUCT_ID)).thenReturn(
        product);
    product.setBlock();

    assertThrows(IllegalArgumentException.class, () -> productService.getProduct(PRODUCT_ID));
  }

  @Test
  @DisplayName("상품 목록 조회 성공")
  void getProducts() {
// given
    Pageable pageable = mock(Pageable.class);
    PageDto pageDto = mock(PageDto.class);

    when(pageDto.toPageable()).thenReturn(pageable);
    when(productRepository.findAllAndPaging(pageable)).thenReturn(Page.empty()); // 수정된 부분

// when
    Page<PagingProductResponse> pagingProductResponse = productService.getProducts(pageDto);

// then
    assertThat(pagingProductResponse).isNotNull();

  }

  @Test
  @DisplayName("상품 업데이트 성공")
  void updateProduct() {
    // given
    User user = mock(User.class);
    Product product = Product.builder()
        .productName("earphone")
        .productPrice(10000L)
        .productStatus(ProductStatus.S)
        .productCategory(ProductCategory.IT)
        .productEnum(ProductEnum.판매_중)
        .user(user)
        .build();

    ProductRequestDto productRequestDto = ProductRequestDto.builder()
        .productName("Americano")
        .productPrice(5000L)
        .productStatus(ProductStatus.S)
        .productCategory(ProductCategory.FOOD)
        .productEnum(ProductEnum.판매완료)
        .build();

    when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

    // when
    productService.updateProduct(product.getId(), productRequestDto, user.getId());

    //then
    assertThat(product.getProductName()).isEqualTo(productRequestDto.getProductName());
    assertThat(product.getProductPrice()).isEqualTo(productRequestDto.getProductPrice());
    assertThat(product.getProductStatus()).isEqualTo(productRequestDto.getProductStatus());

  }

  @Test
  @DisplayName("상품 삭제 성공")
  void deleteProduct() {

    // given
    User user = mock(User.class);
    Product product = mock(Product.class);

    when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
    when(Optional.of(product).get().checkProductWriter(user.getId())).thenReturn(true);

    // when
    productService.deleteProduct(product.getId(), user.getId());
    // then
    verify(productRepository, times(1)).deleteById(product.getId());

  }

  @Test
  @DisplayName("상품 리포지토리 조회 후 상품 반환 성공 테스트")
  void findProductById() {
    //given
    Product product = mock(Product.class);

    when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

    //when
    Product productResultFindById = productService.findProductById(product.getId());

    //then
    assertThat(productResultFindById).isEqualTo(product);
  }

  @Test
  @DisplayName("상품 키워드로 검색하기 테스트")
  void searchProductsByKeyword() {
    // given
    PageDto pageDto = mock(PageDto.class);
    Pageable pageRequest = mock(Pageable.class);

    when(pageDto.toPageable()).thenReturn(pageRequest);
    when(pageDto.getKeyword()).thenReturn("food");
    when(productRepository.searchByKeyword(eq("food"), any(Pageable.class))).thenReturn(
        Page.empty());

    //when
    Page<PagingProductResponse> pagingProductResponses = productService.searchProductsByKeyword(
        pageDto);

    //then
    assertThat(pagingProductResponses).isNotNull();

  }

  @Test
  @DisplayName("사용자 차단 성공 테스트")
  void setBlock() {
    //given
    Long productId = 1L;
    User user = mock(User.class);
    Product product = Product.builder()
        .id(productId)
        .productName("earphone")
        .productPrice(10000L)
        .productStatus(ProductStatus.S)
        .productCategory(ProductCategory.IT)
        .productEnum(ProductEnum.판매_중)
        .user(user)
        .build();

    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    // when
    productService.setBlock(productId);

    //then
    verify(productRepository, times(1)).findById(any());
  }

  @Test
  @DisplayName("내 상품 확인하기 테스트")
  void checkMyProduct() {
    //given
    User user = User.builder().id(1L).build();

    when(productRepository.existsByIdAndUser(2L, user)).thenReturn(true);

    //when
    boolean result = productService.checkMyProduct(2L, user);

    // then
    assertTrue(result);
  }

}

