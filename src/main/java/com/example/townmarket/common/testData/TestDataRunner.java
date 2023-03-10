//package com.example.townmarket.common.testData;
//
//import com.example.townmarket.common.domain.address.entity.Address;
//import com.example.townmarket.common.domain.address.repository.AddressRepository;
//import com.example.townmarket.common.domain.product.entity.Product;
//import com.example.townmarket.common.domain.product.entity.Product.ProductCategory;
//import com.example.townmarket.common.domain.product.entity.Product.ProductEnum;
//import com.example.townmarket.common.domain.product.entity.Product.ProductStatus;
//import com.example.townmarket.common.domain.product.repository.ProductRepository;
//import com.example.townmarket.common.domain.user.entity.Profile;
//import com.example.townmarket.common.domain.user.entity.User;
//import com.example.townmarket.common.domain.user.repository.UserRepository;
//import com.example.townmarket.common.domain.user.service.UserServiceImpl;
//import com.example.townmarket.common.enums.RoleEnum;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Component
//@RequiredArgsConstructor
//public class TestDataRunner implements ApplicationRunner {
//
//  private final UserRepository userRepository;
//  private final ProductRepository productRepository;
//
//  private final UserServiceImpl userService;
//
//  private final AddressRepository addressRepository;
//
//  private final PasswordEncoder passwordEncoder;
//
//  @Override
//  @Transactional
//  public void run(ApplicationArguments args) throws Exception {
//    Profile profile = new Profile("asdsad");
//
//    User user = User.builder()
//        .username("user1")
//        .password(passwordEncoder.encode("Password!23"))
//        .email("asda11as@gmail.com")
//        .role(RoleEnum.MEMBER)
//        .profile(profile)
//        .build();
//    userRepository.save(user);
//
//    Address address = new Address("경기도", "고양시", "일산동구", user);
//
//    addressRepository.save(address);
//
//    Profile profile1 = new Profile("nickname1");
//    User user1 = User.builder()
//        .username("user2")
//        .password(passwordEncoder.encode("Password!23"))
//        .email("asdaas@gmail.com")
//        .role(RoleEnum.MEMBER)
//        .profile(profile1)
//        .build();
//
//    userRepository.save(user1);
//
//    Address address2 = new Address("경기도", "고양시", "일산동구", user1);
//
//    addressRepository.save(address2);
//
//    Profile profile2 = new Profile("nickname2");
//
//    User user2 = User.builder()
//        .username("user3")
//        .password(passwordEncoder.encode("Password!23"))
//        .email("asd1aas@gmail.com")
//        .role(RoleEnum.MEMBER)
//        .profile(profile2)
//        .build();
//    userRepository.save(user2);
//
//    Address address3 = new Address("경기도", "고양시", "일산동구", user2);
//    addressRepository.save(address3);
//
//    Product product1 = Product.builder().productName("product").productPrice(11111L)
//        .productStatus(ProductStatus.S)
//        .productCategory(ProductCategory.IT)
//        .productEnum(ProductEnum.나눔)
//        .user(user)
//        .build();
//    productRepository.save(product1);
//
//    for (int i = 0; i < 3; i++) {
//      String productName = "product" + i;
//      Product product = Product.builder().productName(productName).productPrice(11111L)
//          .productStatus(ProductStatus.S)
//          .productCategory(ProductCategory.IT)
//          .productEnum(ProductEnum.나눔)
//          .user(user)
//          .build();
//      productRepository.save(product);
//    }
//
//  }
//}
