package com.example.townmarket.common.domain.user.repository;

import com.example.townmarket.common.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryQuery {

  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

//  Boolean existsByPhoneNumber(String phoneNumber);

  Boolean existsByEmail(String email);

  Page<User> findAll(Pageable pageable);

//  Optional<User> findByPhoneNumber(String phone);

  Optional<User> findByEmail(String email);


}
