package com.example.townmarket.common.domain.trade.repository;

import com.example.townmarket.common.domain.trade.dto.PagingTrade;
import com.example.townmarket.common.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeRepositoryQuery {


  Page<PagingTrade> findBuyList(Pageable pageable, User buyer);

  Page<PagingTrade> findSaleList(Pageable pageable, User seller);
}
