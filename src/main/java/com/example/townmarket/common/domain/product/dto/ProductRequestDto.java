package com.example.townmarket.common.domain.product.dto;

import com.example.townmarket.common.domain.product.entity.Product.ProductCategory;
import com.example.townmarket.common.domain.product.entity.Product.ProductEnum;
import com.example.townmarket.common.domain.product.entity.Product.ProductStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {

  private String productImg;

  private String productName;

  private long productPrice;

  private ProductStatus productStatus;
  private String productContents;
  private ProductCategory productCategory;

  private ProductEnum productEnum;

  @Builder
  public ProductRequestDto(String productImg, String productName, long productPrice, ProductStatus productStatus,
      String productContents,
      ProductCategory productCategory, ProductEnum productEnum) {
    this.productImg = productImg;
    this.productName = productName;
    this.productPrice = productPrice;
    this.productStatus = productStatus;
    this.productContents = productContents;
    this.productCategory = productCategory;
    this.productEnum = productEnum;
  }
}
