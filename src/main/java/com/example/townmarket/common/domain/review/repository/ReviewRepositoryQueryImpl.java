package com.example.townmarket.common.domain.review.repository;

import static com.example.townmarket.common.domain.review.entity.QReview.review;

import com.example.townmarket.common.domain.review.dto.ReviewResponseDto;
import com.example.townmarket.common.domain.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class ReviewRepositoryQueryImpl implements ReviewRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  @Transactional(readOnly = true)
  public boolean existsReviewId(Long reviewId) {
    return jpaQueryFactory.from(review).where(review.id.eq(reviewId)).select(review.id)
        .setHint("org.hibernate.readOnly", true).fetchFirst() != null;
  }

  @Override
  @Transactional(readOnly = true)
  public ReviewResponseDto searchByReviewId(Long reviewId) {
    return query()
        .where(review.id.eq(reviewId))
        .fetchFirst();
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ReviewResponseDto> searchByUserAndPaging(User reviewer, Pageable pageable) {
    List<ReviewResponseDto> searchByReviewer = query()
        .where(review.reviewer.eq(reviewer))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(review.createdAt.desc())
        .fetch();
    long totalSize = countQuery(reviewer).fetch().get(0);

    return PageableExecutionUtils.getPage(searchByReviewer, pageable, () -> totalSize);
  }

  private JPAQuery<ReviewResponseDto> query() {
    return jpaQueryFactory.select(Projections.constructor(ReviewResponseDto.class,
            review.id,
            review.reviewContents,
            review.product.productName,
            review.userGrade.grade
        ))
        .from(review)
        .setHint("org.hibernate.readOnly", true);
  }

  private JPAQuery<Long> countQuery(User reviewer) {
    return jpaQueryFactory.select(Wildcard.count)
        .from(review)
        .leftJoin(review.reviewer)
        .where(reviewerEq(reviewer));
  }

  private BooleanExpression reviewerEq(User reviewer) {
    return Objects.nonNull(reviewer) ? review.reviewer.eq(reviewer) : null;
  }

}
