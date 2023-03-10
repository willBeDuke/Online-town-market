package com.example.townmarket.common.redis.repository;

import com.example.townmarket.common.redis.entity.Tokens;
import org.springframework.data.repository.CrudRepository;

public interface BlacklistTokenRepository extends CrudRepository<Tokens, String> {

}
