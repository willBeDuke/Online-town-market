package com.example.townmarket.common.domain.trade.service;

import com.example.townmarket.common.domain.product.entity.Product;
import com.example.townmarket.common.domain.product.entity.Product.ProductEnum;
import com.example.townmarket.common.domain.product.repository.ProductRepository;
import com.example.townmarket.common.domain.trade.dto.CreateTradeDto;
import com.example.townmarket.common.domain.trade.dto.PagingTrade;
import com.example.townmarket.common.domain.trade.entity.Trade;
import com.example.townmarket.common.domain.trade.repository.TradeRepository;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

  private final TradeRepository tradeRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  @Override
  @Transactional
  public Page<PagingTrade> getPurchaseList(User buyer, Pageable pageable) {
    Page<PagingTrade> tradePage = tradeRepository.findBuyList(pageable, buyer);
    return tradePage;
  }

  @Override
  @Transactional
  public Page<PagingTrade> getSalesList(User seller, Pageable pageable) {
    Page<PagingTrade> tradePage = tradeRepository.findSaleList(pageable, seller);
    return tradePage;
  }

  @Override
  @Transactional
  public void createTrade(CreateTradeDto createTrade, User seller) {
    Product product = findByProductId(createTrade);
    if(!product.checkProductEnum(product.getProductEnum())) {
      product.updateProductEnum();
      User buyer = findByUserId(createTrade);
      Trade trade = Trade.builder().buyer(buyer).seller(seller).productId(product.getId()).build();
      tradeRepository.save(trade);
    }
    else {
      throw new IllegalArgumentException("해당 상품은 거래할 수 없습니다");
    }
  }

  private User findByUserId(CreateTradeDto createTrade) {
    return userRepository.findById(createTrade.getBuyerId())
        .orElseThrow(() -> new IllegalArgumentException("해당 유저는 존재하지않습니다"));
  }

  private Product findByProductId(CreateTradeDto createTrade) {
    return productRepository.findById(createTrade.getProductId())
        .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을수 없습니다"));
  }


}

