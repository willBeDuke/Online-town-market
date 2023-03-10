package com.example.townmarket.admin.service;

import com.example.townmarket.admin.dto.PagingUserResponse;
import com.example.townmarket.admin.repository.AdminRepository;
import com.example.townmarket.common.dto.PageDto;
import com.example.townmarket.common.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final AdminRepository adminRepository;
  private final UserService userService;

  @Transactional(readOnly = true)
  public Page<PagingUserResponse> viewAllUser(PageDto pageDto) {
    return userService.pagingUsers(pageDto.toPageable()).map(PagingUserResponse::new);
  }
}
