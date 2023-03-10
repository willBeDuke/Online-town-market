package com.example.townmarket.common.domain.trade.repository;

import static com.example.townmarket.common.domain.product.entity.QProduct.product;
import static com.example.townmarket.common.domain.trade.entity.QTrade.trade;

import com.example.townmarket.common.domain.trade.dto.PagingTrade;
import com.example.townmarket.common.domain.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class TradeRepositoryQueryImpl implements TradeRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;

  private JPAQuery<Long> sellerCountQuery(User seller) {
    return jpaQueryFactory.select(Wildcard.count)
        .from(trade)
        .where(trade.seller.eq(seller));
  }

  private JPAQuery<Long> buyerCountQuery(User buyer) {
    return jpaQueryFactory.select(Wildcard.count)
        .from(trade)
        .where(trade.buyer.eq(buyer));
  }

  @Override
  public Page<PagingTrade> findBuyList(Pageable pageable, User buyer) {
    List<PagingTrade> pagingTrades = jpaQueryFactory
        .select(Projections.constructor(PagingTrade.class,
            trade.seller.username,
            product.productName))
        .from(trade)
        .leftJoin(product)
        .on(trade.productId.eq(product.id))
        .where(trade.buyer.eq(buyer))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long totalSize = buyerCountQuery(buyer).fetch().get(0);
    return PageableExecutionUtils.getPage(pagingTrades, pageable, () -> totalSize);
  }

  @Override
  public Page<PagingTrade> findSaleList(Pageable pageable, User seller) {
    List<PagingTrade> pagingTrades = jpaQueryFactory
        .select(Projections.constructor(PagingTrade.class,
            trade.buyer.username,
            product.productName))
        .from(trade)
        .leftJoin(product)
        .on(trade.productId.eq(product.id))
        .where(trade.seller.eq(seller))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long totalSize = sellerCountQuery(seller).fetch().get(0);
    return PageableExecutionUtils.getPage(pagingTrades, pageable, () -> totalSize);
  }
}
