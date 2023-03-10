package com.example.townmarket.common.domain.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.townmarket.admin.dto.PagingUserResponse;
import com.example.townmarket.admin.service.AdminServiceImpl;
import com.example.townmarket.common.dto.PageDto;
import com.example.townmarket.common.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

  @InjectMocks
  AdminServiceImpl adminService;
  @Mock
  UserServiceImpl userService;

  @Test
  @DisplayName("유저 목록 조회")
  void viewAllUser() {
    //given
    PageDto pageDto = mock(PageDto.class);
    Pageable pageable = mock(Pageable.class);
    when(pageDto.toPageable()).thenReturn(pageable);
    when(userService.pagingUsers(pageable)).thenReturn(Page.empty());

    //when
    Page<PagingUserResponse> users = adminService.viewAllUser(pageDto);

    //then
    assertThat(users).isEmpty();

    //verify
    verify(userService).pagingUsers(pageable);
  }
}
