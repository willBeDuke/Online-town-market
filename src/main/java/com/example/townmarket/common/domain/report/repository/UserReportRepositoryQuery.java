package com.example.townmarket.common.domain.report.repository;

import com.example.townmarket.admin.dto.PagingUserResponse;
import com.example.townmarket.common.domain.report.dto.PagingUserReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserReportRepositoryQuery {

  Page<PagingUserReportResponse> findAllReportedUserMax(Pageable pageable);

}
