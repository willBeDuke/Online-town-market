package com.example.townmarket.common.domain.email.repository;

import com.example.townmarket.common.domain.email.entity.EmailVerify;
import org.springframework.data.repository.CrudRepository;

public interface EmailVerifyRepository extends CrudRepository<EmailVerify, String> {

}
