package com.example.townmarket.common.domain.report.repository;


import static com.example.townmarket.common.domain.product.entity.QProduct.product;
import static com.example.townmarket.common.domain.report.entity.QUserReport.userReport;

import com.example.townmarket.common.domain.report.dto.PagingUserReportResponse;
import com.example.townmarket.common.domain.trade.entity.QTrade;
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
public class UserReportRepositoryQueryImpl implements UserReportRepositoryQuery{

  private final JPAQueryFactory jpaQueryFactory;


  @Override
  public Page<PagingUserReportResponse> findAllReportedUserMax(Pageable pageable) {
    List<PagingUserReportResponse> pagingUserReportResponses =
        jpaQueryFactory.select(Projections.constructor(PagingUserReportResponse.class,
                userReport.reportedUser.username,
                userReport.reason,
                userReport.reportEnum))
            .from(userReport)
            .where(userReport.reportedUser.count().goe(15))
            .fetch();

    Long totalSize = countQuery().fetch().get(0);
    return PageableExecutionUtils.getPage(pagingUserReportResponses, pageable, () -> totalSize);
  }

  private JPAQuery<Long> countQuery() {
    return jpaQueryFactory.select(Wildcard.count)
        .from(userReport);
  }
}
