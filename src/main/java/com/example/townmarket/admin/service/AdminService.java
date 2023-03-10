package com.example.townmarket.admin.service;

import com.example.townmarket.admin.dto.PagingUserResponse;
import com.example.townmarket.common.dto.PageDto;
import org.springframework.data.domain.Page;

public interface AdminService {

  Page<PagingUserResponse> viewAllUser(PageDto pageDto);

}
