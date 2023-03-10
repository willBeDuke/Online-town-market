package com.example.townmarket.common.domain.product.service;

import com.example.townmarket.common.domain.product.dto.PagingProductResponse;
import com.example.townmarket.common.domain.product.dto.ProductRequestDto;
import com.example.townmarket.common.domain.product.dto.ProductResponseDto;
import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.dto.PageDto;
import org.springframework.data.domain.Page;

public interface ProductService {

  // 상품 생성
  void addProduct(User user, ProductRequestDto productDto);

  // 단일 상품 조회
  ProductResponseDto getProduct(Long productId);

  // 전체 상품 조회
  Page<PagingProductResponse> getProducts(PageDto pageDto);

  // 상품 내용 수정
  void updateProduct(Long productId, ProductRequestDto productDto, Long userId);

  // 상품 삭제
  void deleteProduct(Long productId, Long userId);

  Product findProductById(Long productId);

  void setBlock(Long productId);

  Page<PagingProductResponse> searchProductsByKeyword(PageDto pageDto);

  boolean checkMyProduct(Long productId, User user);
}
