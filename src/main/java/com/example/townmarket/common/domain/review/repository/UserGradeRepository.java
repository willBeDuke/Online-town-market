package com.example.townmarket.common.domain.review.repository;

import com.example.townmarket.common.domain.review.entity.UserGrade;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {

  Set<UserGrade> findAllByReviewee(User reviewee);

}
