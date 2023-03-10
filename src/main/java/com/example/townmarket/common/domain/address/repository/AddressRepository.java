package com.example.townmarket.common.domain.address.repository;

import com.example.townmarket.common.domain.address.entity.Address;
import com.example.townmarket.common.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

  Address findByUser(User user);

}
