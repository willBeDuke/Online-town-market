package com.example.townmarket.common.domain.report.repository;

import com.example.townmarket.common.domain.report.entity.UserReport;
import com.example.townmarket.common.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReportRepository extends JpaRepository<UserReport, Long>, UserReportRepositoryQuery {

  boolean existsByReporterIdAndReportedUser(Long reporterId, User ReportedUser);
}
