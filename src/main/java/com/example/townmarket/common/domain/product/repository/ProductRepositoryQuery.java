package com.example.townmarket.common.domain.product.repository;

import com.example.townmarket.common.domain.product.dto.PagingProductResponse;
import com.example.townmarket.common.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryQuery {

  Page<PagingProductResponse> findAllAndPaging(Pageable pageable);

  Page<PagingProductResponse> searchByKeyword(String keyword, Pageable pageable);

  Product getProductAndSellerProfileByProductIdAndCountView(Long productId);

  Long InterestCount(Product product);
}
