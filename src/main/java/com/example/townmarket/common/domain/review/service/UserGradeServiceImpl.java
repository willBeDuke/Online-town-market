package com.example.townmarket.common.domain.review.service;

import com.example.townmarket.common.domain.review.entity.UserGrade;
import com.example.townmarket.common.domain.review.repository.UserGradeRepository;
import com.example.townmarket.common.domain.user.entity.User;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGradeServiceImpl implements UserGradeService {

  private final UserGradeRepository userGradeRepository;

  @Override
  public void saveGrade(UserGrade userGrade) {
    userGradeRepository.save(userGrade);
  }

  @Override
  @Transactional
  public void updateGrade(UserGrade userGrade, int grade) {
    userGrade.setGrade(grade);
  }

  @Override
  @Transactional(readOnly = true)
  public Set<UserGrade> findAllByReviewee(User Reviewee) {
    return userGradeRepository.findAllByReviewee(Reviewee);
  }
}
