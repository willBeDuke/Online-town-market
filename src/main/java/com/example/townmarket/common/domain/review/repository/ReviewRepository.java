package com.example.townmarket.common.domain.review.repository;

import com.example.townmarket.common.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Review.class, idClass = Long.class)
public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryQuery {


  boolean existsReviewByProductId(Long productId);

//  @Query(
//      "select new com.example.townmarket.review.dto.ReviewResponseDto(r.grade,r.reviewContents,r.reviewer.profile,r.reviewee.profile,p.productName) "
//          + "from Review r join fetch User u "
//          + "left join Product p on r.productId = p.id "
//          + "where r.id = :reviewId")
//  ReviewResponseDto findProfileAndReviewByReviewId(Long reviewId);
//
//  @Query(
//      "select new com.example.townmarket.review.dto.ReviewResponseDto(r.grade,r.reviewContents,r.reviewer.profile,r.reviewee.profile,p.productName) "
//          + "from Review r join fetch User u "
//          + "left join Product p on r.productId = p.id "
//          + "where r.reviewer = :user")
//  Page<ReviewResponseDto> findProfileAndReviewByReviewIdPaging(Pageable pageable, User user);

}
