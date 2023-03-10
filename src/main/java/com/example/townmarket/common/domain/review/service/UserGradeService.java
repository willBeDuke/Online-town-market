package com.example.townmarket.common.domain.review.service;

import com.example.townmarket.common.domain.review.entity.UserGrade;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.Set;

public interface UserGradeService {

  void saveGrade(UserGrade userGrade);

  void updateGrade(UserGrade userGrade, int grade);

  Set<UserGrade> findAllByReviewee(User Reviewee);
}
