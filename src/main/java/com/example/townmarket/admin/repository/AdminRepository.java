package com.example.townmarket.admin.repository;

import com.example.townmarket.admin.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

  Optional<Admin> findByUsername(String username);
}
