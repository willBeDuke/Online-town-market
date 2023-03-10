package com.example.townmarket.common.domain.admin.controller;


import static com.example.townmarket.fixture.ReportFixture.PAGING_USER_REPORT_RESPONSE_LIST;
import static com.example.townmarket.fixture.UserFixture.PAGING_USER_RESPONSE1;
import static com.example.townmarket.fixture.UserFixture.PAGING_USER_RESPONSES;
import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentRequest;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.townmarket.admin.controller.AdminController;
import com.example.townmarket.admin.service.AdminServiceImpl;
import com.example.townmarket.annotation.WithCustomMockAdmin;
import com.example.townmarket.common.domain.report.sevice.UserReportService;
import com.example.townmarket.common.globalException.ExceptionController;
import com.example.townmarket.common.util.SetHttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(controllers = AdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AdminControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @MockBean
  ExceptionController exceptionController;
  @MockBean
  AdminServiceImpl adminService;
  @MockBean
  UserReportService userReportService;

  @MockBean
  SetHttpHeaders httpHeaders;

  @Test
  @DisplayName("유저 목록 조회")
  @WithCustomMockAdmin
  void viewAllUser() throws Exception {
    Pageable pageable = PAGE_DTO.toPageable();
    // 직렬화
    String json = objectMapper.writeValueAsString(PAGING_USER_RESPONSES);

    given(adminService.viewAllUser(any())).willReturn(PAGING_USER_RESPONSES);

    //when
    ResultActions resultActions = mockMvc.perform(get("/admin/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(PAGE_DTO))
        .with(csrf()))
        .andExpect(status().isOk());
    resultActions.andDo(document("adminController/viewAllUser",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지"),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description("글의 갯수"),
            fieldWithPath("keyword").type(JsonFieldType.STRING).description("키워드"),
            fieldWithPath("sortBy").type(JsonFieldType.STRING).description("정렬기준"),
            fieldWithPath("asc").type(JsonFieldType.BOOLEAN).description("오름/내림차순")
        ),
        responseFields(
            fieldWithPath("content[].username").type(JsonFieldType.STRING).description("유저 아이디"),
            fieldWithPath("content[].profile.nickName").type(JsonFieldType.STRING).description("프로필 닉네임"),
            fieldWithPath("content[].profile.img_url").type(JsonFieldType.STRING).description("프로필 이미지 URL"),
            fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("last").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("number").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("first").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("")

//            fieldWithPath("pageable").ignored(),
//            fieldWithPath("last").ignored(),
//            fieldWithPath("totalPages").ignored(),
//            fieldWithPath("totalElements").ignored(),
//            fieldWithPath("size").ignored(),
//            fieldWithPath("number").ignored(),
//            fieldWithPath("sort").ignored(),
//            fieldWithPath("first").ignored(),
//            fieldWithPath("numberOfElements").ignored(),
//            fieldWithPath("empty").ignored()
//        )
        )
    ));

  }

  @Test
  @WithCustomMockAdmin
  @DisplayName("신고 당한 유저 리스트 조회")
  void viewAllReportedUser() throws Exception {
    Pageable pageable = PAGE_DTO.toPageable();

    String json = objectMapper.writeValueAsString(PAGING_USER_REPORT_RESPONSE_LIST);

    given(userReportService.viewAllReportedUser(any())).willReturn(
        PAGING_USER_REPORT_RESPONSE_LIST);

    ResultActions resultActions = mockMvc.perform(get("/admin/report/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PAGE_DTO))
            .with(csrf()))
        .andExpect(status().isOk());

    resultActions.andDo(document("adminController/viewAllReportUser",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지"),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description("글의 갯수"),
            fieldWithPath("keyword").type(JsonFieldType.STRING).description("키워드"),
            fieldWithPath("sortBy").type(JsonFieldType.STRING).description("정렬기준"),
            fieldWithPath("asc").type(JsonFieldType.BOOLEAN).description("오름/내림차순")
        ),
        responseFields(
            fieldWithPath("content[].reportedName").type(JsonFieldType.STRING).description("신고당한 유저아이디"),
            fieldWithPath("content[].reason").type(JsonFieldType.STRING).description("이유"),
            fieldWithPath("content[].reportEnum").type(JsonFieldType.STRING).description("신고 사유"),
            fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("last").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("number").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("first").type(JsonFieldType.BOOLEAN).description(""),
            fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description(""),
            fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("")

//            fieldWithPath("pageable").ignored(),
//            fieldWithPath("last").ignored(),
//            fieldWithPath("totalPages").ignored(),
//            fieldWithPath("totalElements").ignored(),
//            fieldWithPath("size").ignored(),
//            fieldWithPath("number").ignored(),
//            fieldWithPath("sort").ignored(),
//            fieldWithPath("first").ignored(),
//            fieldWithPath("numberOfElements").ignored(),
//            fieldWithPath("empty").ignored()
//        )
        )
    ));


  }

}
