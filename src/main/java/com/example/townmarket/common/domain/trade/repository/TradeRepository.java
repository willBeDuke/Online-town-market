package com.example.townmarket.common.domain.trade.repository;

import com.example.townmarket.common.domain.trade.entity.Trade;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long>, TradeRepositoryQuery {

//  Page<Trade> findAllBySeller(Pageable pageable, User Seller);

  Page<Trade> findAllByBuyer(Pageable pageable, User buyer);

}
