package com.example.townmarket.common.domain.s3.controller;

import com.example.townmarket.common.domain.s3.service.S3Service;
import com.example.townmarket.common.domain.s3.service.S3ServiceImpl;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3Controller {

  private final S3ServiceImpl s3Service;

  // 이미지 프로필용
  @PostMapping("/api/images/upload/profiles")
  public String imageUploadProfile(@RequestParam("profile") MultipartFile multipartFile) throws IOException {
    String s = s3Service.upload(multipartFile, "online-town-market", "profile");
    System.out.println(s);
    return s;
  }

  // 이미지 상품용
  @PostMapping("/api/images/upload/products")
  public String imageUploadProduct(@RequestParam("product") MultipartFile multipartFile) throws IOException {
    String s = s3Service.upload(multipartFile, "online-town-market", "product");
    System.out.println(s);
    return s;
  }

}
