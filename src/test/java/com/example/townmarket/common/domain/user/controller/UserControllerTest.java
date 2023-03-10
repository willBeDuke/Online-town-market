package com.example.townmarket.common.domain.user.controller;

import static com.example.townmarket.common.domain.user.controller.UserController.USER_API_URI;
import static com.example.townmarket.fixture.UserFixture.DUPLICATE_CHECK_REQUEST_DTO;
import static com.example.townmarket.fixture.UserFixture.DUPLICATE_CHECK_RESPONSE_DTO;
import static com.example.townmarket.fixture.UserFixture.LOGIN_REQUEST_DTO;
import static com.example.townmarket.fixture.UserFixture.MEMBER_UNIQUE_ID;
import static com.example.townmarket.fixture.UserFixture.PASSWORD_UPDATE_REQUEST_DTO;
import static com.example.townmarket.fixture.UserFixture.PROFILE_RESPONSE_DTO;
import static com.example.townmarket.fixture.UserFixture.SIGNUP_REQUEST_DTO;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentRequest;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.townmarket.annotation.WithCustomMockUser;
import com.example.townmarket.common.domain.user.service.UserService;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(controllers = UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class UserControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  UserService userService;

  @MockBean
  SetHttpHeaders setHttpHeaders;

  @Test
  @WithMockUser
  @DisplayName("회원가입 성공 상태코드 201 반환")
  void signup() throws Exception {

    doNothing().when(userService).signup(SIGNUP_REQUEST_DTO);

    ResultActions resultActions = mockMvc.perform(post(USER_API_URI + "/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(SIGNUP_REQUEST_DTO))
        .with(csrf()));

    resultActions.andExpect(status().isCreated());

    resultActions.andDo(document("userController/signup",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("username").type(JsonFieldType.STRING).description("유저 이름"),
            fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
            fieldWithPath("img_url").type(JsonFieldType.STRING).description("프로필 이미지"),
            fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
            fieldWithPath("address1").type(JsonFieldType.STRING).description("지역"),
            fieldWithPath("address2").type(JsonFieldType.STRING).description("지역"),
            fieldWithPath("address3").type(JsonFieldType.STRING).description("지역")
        )
    ));

  }


  @Test
  @WithMockUser
  @DisplayName("로그인 성공시 200반환")
  void login() throws Exception {

//    doNothing().when(userService).login(any(), LOGIN_REQUEST_DTO);

    ResultActions resultActions = mockMvc.perform(
            post(USER_API_URI + "/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(LOGIN_REQUEST_DTO))
                .with(csrf()))
        .andExpect(status().isOk());

    resultActions
        .andDo(document("userController/login",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("username").type(JsonFieldType.STRING).description("유저 이름"),
                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 반환 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메시지")
            )
        ));


  }

  @Test
  @WithMockUser
  @DisplayName("로그아웃 성공시 200반환")
  void logout() throws Exception {
    doNothing().when(userService).logout(any(), any());

    ResultActions resultActions = mockMvc.perform(
            post(USER_API_URI + "/logout")
                .with(csrf()))
        .andExpect(status().isOk());

    resultActions
        .andDo(document("userController/logout",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 반환 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메시지")
            )
        ));

  }

  @Test
  @WithCustomMockUser
  @DisplayName("비밀번호 변경 성공시 200 반환")
  void updateUser() throws Exception {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    doNothing().when(userService)
        .updateUser(userDetails.getUsername(), PASSWORD_UPDATE_REQUEST_DTO);
    ResultActions resultActions = mockMvc.perform(
            put(USER_API_URI + "/update/pw")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PASSWORD_UPDATE_REQUEST_DTO))
                .with(csrf()))
        .andExpect(status().isOk());

    resultActions
        .andDo(document("userController/update-pw",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("password").type(JsonFieldType.STRING).description("변경되는 비밀번호")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 반환 코드"),
                fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메시지")
            )
        ));
  }

//  @Test
//  @WithCustomMockUser
//  @DisplayName("지역 업데이트 성공시 200 반환")
//  void updateRegion() throws Exception {
//
//    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
//        .getAuthentication().getPrincipal();
//    doNothing().when(userService)
//        .updateRegion(userDetails.getUsername(), REGION_UPDATE_REQUEST_DTO);
//
//    ResultActions resultActions = mockMvc.perform(
//            put(USER_API_URI + "/update/region")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(REGION_UPDATE_REQUEST_DTO))
//                .with(csrf()))
//        .andExpect(status().isOk());
//
//    resultActions
//        .andDo(document("usercontroller/update-region",
//            getDocumentRequest(),
//            getDocumentResponse(),
//            requestFields(
//                fieldWithPath("region").type(JsonFieldType.STRING).description("변경되는 지역")
//            ),
//            responseFields(
//                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 반환 코드"),
//                fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메시지")
//            )
//        ));
//  }

  @Test
  @WithCustomMockUser
  @DisplayName("삭제 성공시 204 반환")
  void deleteUser() throws Exception {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    ResultActions resultActions = mockMvc.perform(
            delete(USER_API_URI + "/{userId}", MEMBER_UNIQUE_ID)
                .with(csrf()))
        .andExpect(status().isNoContent());

    resultActions
        .andDo(document("userController/delete",
            getDocumentRequest(),
            getDocumentResponse()
        ));
  }

  @Test
  @WithCustomMockUser
  void updateProfile() throws Exception {
    given(userService.showProfile(MEMBER_UNIQUE_ID)).willReturn(PROFILE_RESPONSE_DTO);

    ResultActions resultActions = mockMvc.perform(
            get(USER_API_URI + "/profile/{userId}", MEMBER_UNIQUE_ID)
                .with(csrf()))
        .andExpect(status().isOk());

    resultActions
        .andDo(document("userController/show-profile",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("img_url").type(JsonFieldType.STRING).description("프로필 사진")
            )
        ));
  }

  @Test
  @WithCustomMockUser
  @DisplayName("유저 프로필 조회")
  void showProfile() throws Exception {
    UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();

    given(userService.getMyProfile(userDetails.getUsername())).willReturn(PROFILE_RESPONSE_DTO);

    ResultActions resultActions = mockMvc.perform(
            get(USER_API_URI + "/profile")
                .with(csrf()))
        .andExpect(status().isOk());

    resultActions
        .andDo(document("userController/get-my-profile",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
                fieldWithPath("img_url").type(JsonFieldType.STRING).description("프로필 사진")
            )
        ));
  }

  @Test
  @WithMockUser
  @DisplayName("중복 없을 시 200반환")
  void duplicateCheck() throws Exception {

    // UserService의 duplicateCheck 메서드가 호출될 때 반환할 값을 설정합니다.
    when(userService.duplicateCheck(DUPLICATE_CHECK_REQUEST_DTO)).thenReturn(
        DUPLICATE_CHECK_RESPONSE_DTO);

    // API 호출을 수행하고 결과를 검증합니다.
    mockMvc.perform(post(USER_API_URI + "/duplicate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(DUPLICATE_CHECK_REQUEST_DTO))
            .with(csrf()))
        .andExpect(status().isOk())
        // REST Docs를 사용하여 API 문서를 생성합니다.
        .andDo(document("userController/duplicate",
            getDocumentRequest(),
            getDocumentResponse(),
            requestFields(
                fieldWithPath("duplicateField").description("중복 체크 필드 이름"),
                fieldWithPath("content").description("중복 체크 필드 값")
            )
        ));
  }

}
