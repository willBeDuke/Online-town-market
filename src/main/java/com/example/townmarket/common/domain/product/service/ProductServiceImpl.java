package com.example.townmarket.common.domain.product.service;

import com.example.townmarket.common.domain.product.dto.PagingProductResponse;
import com.example.townmarket.common.domain.product.dto.ProductRequestDto;
import com.example.townmarket.common.domain.product.dto.ProductResponseDto;
import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.product.repository.ProductRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.dto.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;


  @Override
  @Transactional
  public void addProduct(User user, ProductRequestDto productRequestDto) {
    Product product = Product.builder()
        .productName(productRequestDto.getProductName())
        .productPrice(productRequestDto.getProductPrice())
        .productStatus(productRequestDto.getProductStatus())
        .productCategory(productRequestDto.getProductCategory())
        .productContents(productRequestDto.getProductContents())
        .productEnum(productRequestDto.getProductEnum())
        .productImg(productRequestDto.getProductImg())
        .user(user)
        .build();
    productRepository.save(product);
  }

  @Override
  @Transactional
  public ProductResponseDto getProduct(Long productId) {
    Product product = productRepository.getProductAndSellerProfileByProductIdAndCountView(
        productId);
    isBlock(product);
    ProductResponseDto productResponseDto = ProductResponseDto.valueOf(product);
    return productResponseDto;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PagingProductResponse> getProducts(PageDto pageDto) {
    return productRepository.findAllAndPaging(pageDto.toPageable());
  }

  @Override
  @Transactional
  public void updateProduct(Long productId, ProductRequestDto productDto, Long userId) {
    Product product = findProductById(productId);
    isBlock(product);
    if (!product.checkProductWriter(userId)) {
      throw new IllegalArgumentException("본인의 상품이 아닙니다");
    }
    product.update(productDto);
  }

  @Override
  @Transactional
  public void deleteProduct(Long productId, Long userId) {
    Product product = findProductById(productId);
    isBlock(product);
    if (!product.checkProductWriter(userId)) {
      throw new IllegalArgumentException("본인의 상품이 아닙니다");
    }
    productRepository.deleteById(product.getId());
  }

  @Override
  @Transactional(readOnly = true)
  public Product findProductById(Long productId) {
    return productRepository.findById(productId).orElseThrow(
        () -> new IllegalArgumentException("존재하지 않는 상품입니다")
    );
  }

  @Override
  public void setBlock(Long productId) {
    findProductById(productId).setBlock();
  }

  @Override
  public Page<PagingProductResponse> searchProductsByKeyword(PageDto pageDto) {
    return productRepository.searchByKeyword(pageDto.getKeyword(), pageDto.toPageable());
  }

  @Override
  public boolean checkMyProduct(Long productId, User user) {
    return productRepository.existsByIdAndUser(productId, user);
  }

  private void isBlock(Product product) {
    if (product.isBlocked()) {
      throw new IllegalArgumentException("신고가 누적되어 접근이 제한되었습니다");
    }
  }
}
