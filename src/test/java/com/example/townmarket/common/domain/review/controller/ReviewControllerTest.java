package com.example.townmarket.common.domain.review.controller;


import static com.example.townmarket.fixture.ReviewFixture.CREATE_REVIEW_REQUEST_DTO;
import static com.example.townmarket.fixture.ReviewFixture.REVIEW_ID;
import static com.example.townmarket.fixture.ReviewFixture.REVIEW_RESPONSE_DTO;
import static com.example.townmarket.fixture.ReviewFixture.REVIEW_RESPONSE_DTO_PAGE;
import static com.example.townmarket.fixture.ReviewFixture.UPDATE_REVIEW_REQUEST_DTO;
import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentRequest;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.townmarket.annotation.WithCustomMockUser;
import com.example.townmarket.common.domain.review.dto.CreateReviewRequestDto;
import com.example.townmarket.common.domain.review.dto.ReviewResponseDto;
import com.example.townmarket.common.domain.review.dto.UpdateReviewRequestDto;
import com.example.townmarket.common.domain.review.service.ReviewServiceImpl;
import com.example.townmarket.common.domain.user.entity.User;
import com.example.townmarket.common.dto.PageDto;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.enums.ResponseMessages;
import com.example.townmarket.common.security.UserDetailsImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(controllers = ReviewController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ReviewControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  ReviewServiceImpl reviewService;

  @MockBean
  SetHttpHeaders setHttpHeaders;

  @Test
  @WithCustomMockUser
  void createReview() throws Exception {
    ResultActions resultActions = mockMvc.perform(post("/review")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(CREATE_REVIEW_REQUEST_DTO))
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isCreated());

    resultActions.andDo(document("reviewController/createReview",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("revieweeId").type(JsonFieldType.NUMBER).description("리뷰_작성자"),
            fieldWithPath("grade").type(JsonFieldType.NUMBER).description("평가_점수"),
            fieldWithPath("productId").type(JsonFieldType.NUMBER).description("상품_ID"),
            fieldWithPath("reviewContents").type(JsonFieldType.STRING).description("리뷰_내용")
        )
    ));
  }

  @Test
  @WithCustomMockUser
  void showSelectReview() throws Exception {

    given(reviewService.showSelectReview(any())).willReturn(REVIEW_RESPONSE_DTO);

    ResultActions resultActions = mockMvc.perform(get("/review/{reviewId}", REVIEW_ID)
            .with(csrf()))
        .andExpect(status().isOk());

    resultActions.andDo(document("reviewController/showSelectReview",
        getDocumentRequest(),
        getDocumentResponse(),
        responseFields(
            fieldWithPath("reviewId").type(JsonFieldType.NUMBER)
                .description("리뷰 Id"),
            fieldWithPath("productName").type(JsonFieldType.STRING).description("상품 이름"),
            fieldWithPath("reviewContents").type(JsonFieldType.STRING)
                .description("리뷰작성자_프로필_이미지"),
            fieldWithPath("grade").type(JsonFieldType.NUMBER)
                .description("평점")
        )
    ));
  }

  @Test
  @WithCustomMockUser
  void showMyReviews() throws Exception {

/***
 * 테스트 코드 내부에 json 파일 - 해당 실제 응답값이 담겨있는 파일-을 읽어와서 해당 willReturn에 넣어주기
 * 응답값이 있는 파일을 읽어오기
 * objectmapper를 통해 해당 읽어온 파일을 객체 클래스로 변환, willReturn에 넣어주기
 * 파일을 객체 클래스로? 아니면 해당 json 응답값을 string으로 변환 후 응답값에 넣어주기
 */

    String json = objectMapper.writeValueAsString(REVIEW_RESPONSE_DTO_PAGE);
    given(reviewService.showMyReviews(any(),any())).willReturn(REVIEW_RESPONSE_DTO_PAGE);


    ResultActions resultActions = mockMvc.perform(get("/review/my")
            .content(objectMapper.writeValueAsBytes(PAGE_DTO))
            .with(csrf()))
        .andExpect(status().isOk());

    resultActions.andDo(document("reviewController/showMyReviews",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지"),
            fieldWithPath("size").type(JsonFieldType.NUMBER).description("글의 갯수"),
            fieldWithPath("keyword").type(JsonFieldType.STRING).description("키워드"),
            fieldWithPath("sortBy").type(JsonFieldType.STRING).description("리뷰내용"),
            fieldWithPath("asc").type(JsonFieldType.BOOLEAN).description("리뷰내용")
        ),
        responseFields(

            fieldWithPath("content[].reviewId").type(JsonFieldType.NUMBER).description("리뷰 id"),
            fieldWithPath("content[].productName").type(JsonFieldType.STRING).description("상품이름"),
            fieldWithPath("content[].reviewContents").type(JsonFieldType.STRING).description("리뷰 내용"),
            fieldWithPath("content[].grade").type(JsonFieldType.NUMBER).description("평점"),

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
        )));
  }


  @Test
  @WithCustomMockUser
  void updateMyReview() throws Exception {

    ResultActions resultActions = mockMvc.perform(patch("/review/update/{reviewId}", REVIEW_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(UPDATE_REVIEW_REQUEST_DTO))
            .accept(MediaType.APPLICATION_JSON)
            .with(csrf()))
        .andExpect(status().isOk());

    resultActions.andDo(document("reviewController/updateMyReview",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("reviewContents").type(JsonFieldType.STRING).description("리뷰 내용"),
            fieldWithPath("grade").type(JsonFieldType.NUMBER).description("평점")
        ),
        responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("메시지")
        )
    ));
  }

  @Test
  @WithCustomMockUser
  void deleteMyReview() throws Exception {
    ResultActions resultActions = mockMvc.perform(delete("/review/delete/{reviewId}", REVIEW_ID)
            .with(csrf()))
        .andExpect(status().isNoContent());
    resultActions.andDo(document("reviewController/deleteMyReview",
        getDocumentRequest(),
        getDocumentResponse()));
  }
}
