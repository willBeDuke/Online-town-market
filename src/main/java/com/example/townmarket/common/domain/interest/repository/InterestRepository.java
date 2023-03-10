package com.example.townmarket.common.domain.interest.repository;

import com.example.townmarket.common.domain.interest.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Interest.class, idClass = Long.class)
public interface InterestRepository extends JpaRepository<Interest, Long>, InterestRepositoryQuery {

  void deleteByProductId(Long productId);

}
