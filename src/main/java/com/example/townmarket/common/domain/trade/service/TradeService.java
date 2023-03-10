package com.example.townmarket.common.domain.trade.service;

import com.example.townmarket.common.domain.trade.dto.CreateTradeDto;
import com.example.townmarket.common.domain.trade.dto.PagingTrade;
import com.example.townmarket.common.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TradeService {

  Page<PagingTrade> getPurchaseList(User user, Pageable pageable);

  Page<PagingTrade> getSalesList(User user, Pageable pageable);


//  Page<PagingTrade> getSalesListOfOther(Long userId, Pageable pageable);

  void createTrade(CreateTradeDto createTrade, User user);
}
