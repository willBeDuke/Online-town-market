package com.example.townmarket.common.domain.interest.repository;

import static com.example.townmarket.common.domain.interest.entity.QInterest.interest;

import com.example.townmarket.common.domain.interest.dto.InterestPagingResponseDto;
import com.example.townmarket.common.domain.product.entity.Product;
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
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class InterestRepositoryQueryImpl implements InterestRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUserAndProduct(User user, Product product) {
    return jpaQueryFactory.from(interest)
        .where(interest.user.eq(user), interest.product.eq(product))
        .select(interest.user, interest.product)
        .leftJoin(interest.product)
        .leftJoin(interest.user)
        .setHint("org.hibernate.readOnly", true)
        .fetchFirst() != null;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<InterestPagingResponseDto> searchInterestIndexByUser(User user, Pageable pageable) {
    List<InterestPagingResponseDto> interestPagingResponseDto = query(user)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(interest.createdAt.desc())
        .setHint("org.hibernate.readOnly", true)
        .fetch();
    long totalSize = countQuery(user).fetch().get(0);

    return PageableExecutionUtils.getPage(interestPagingResponseDto, pageable, () -> totalSize);
  }


  private JPAQuery<InterestPagingResponseDto> query(User user) {
    return jpaQueryFactory.select(Projections.constructor(InterestPagingResponseDto.class,
            interest.product.productImg,
            interest.product.productName,
            interest.product.productPrice,
            interest.product.id))
        .from(interest)
        .leftJoin(interest.product)
        .where(interest.user.eq(user));
  }

  private JPAQuery<Long> countQuery(User user) {
    return jpaQueryFactory.select(Wildcard.count)
        .from(interest)
        .where(interest.user.eq(user));
  }

}
