package com.example.townmarket.common.domain.product.repository;

import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Product.class, idClass = Long.class)
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryQuery {

  Product findByUser(User sellerName);

  Page<Product> findAll(Pageable pageable);

  boolean existsByIdAndUser(Long productId, User user);
}

