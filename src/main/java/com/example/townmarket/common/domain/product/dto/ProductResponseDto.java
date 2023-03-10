package com.example.townmarket.common.domain.product.dto;

import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.product.entity.Product.ProductCategory;
import com.example.townmarket.common.domain.product.entity.Product.ProductEnum;
import com.example.townmarket.common.domain.product.entity.Product.ProductStatus;
import com.example.townmarket.common.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ProductResponseDto {

  private long productId;
  private long sellerId;

  private String productName;

  private long productPrice;

  private ProductStatus productStatus;

  private ProductCategory productCategory;

  private ProductEnum productEnum;

  private String productImg;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  private Long viewCount;
  String productContents;
  private String nickName;
  private String img;
  private String region;
  private double userGrade;
  private Long interest;

  public static ProductResponseDto valueOf(Product product) {
    return new ProductResponseDto(product.getId(),
        product.getUser().getId(),
        product.getProductName(),
        product.getProductPrice(),
        product.getProductStatus(),
        product.getProductCategory(),
        product.getProductEnum(),
        product.getProductImg(),
        product.getCreatedAt(),
        product.getModifiedAt(),
        product.getViewCount(),
        product.getProductContents(),
        product.getUser().getProfile().getNickName(),
        product.getUser().getProfile().getImg_url(),
        product.getUser().getAddress().get(0).getAddress3(),
        product.getUser().getUserAverageGrade(),
        (long) product.getInterest().size());
  }

  public ProductResponseDto(Product product, User user) {
    this.productId = product.getId();
    this.sellerId = product.getUser().getId();
    this.productName = product.getProductName();
    this.productPrice = product.getProductPrice();
    this.productStatus = product.getProductStatus();
    this.productCategory = product.getProductCategory();
    this.productEnum = product.getProductEnum();
    this.productImg = product.getProductImg();
    this.createdAt = product.getCreatedAt();
    this.modifiedAt = product.getModifiedAt();
    this.viewCount = product.getViewCount();
    this.productContents = product.getProductContents();
    this.nickName = user.getProfile().getNickName();
    this.img = user.getProfile().getImg_url();
    this.region = user.getAddress().get(0).getAddress3();
    this.userGrade = user.getUserAverageGrade();
    this.interest = (long) product.getInterest().size();
  }
}
