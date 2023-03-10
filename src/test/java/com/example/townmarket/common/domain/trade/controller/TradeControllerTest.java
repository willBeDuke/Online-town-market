package com.example.townmarket.common.domain.trade.controller;

import static com.example.townmarket.fixture.TradeFixture.CREATE_TRADE_DTO;
import static com.example.townmarket.fixture.TradeFixture.PAGING_TRADE_PAGE;
import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentRequest;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.townmarket.admin.controller.AdminController;
import com.example.townmarket.admin.service.AdminServiceImpl;
import com.example.townmarket.annotation.WithCustomMockUser;
import com.example.townmarket.common.domain.report.sevice.UserReportService;
import com.example.townmarket.common.domain.trade.service.TradeService;
import com.example.townmarket.common.globalException.ExceptionController;
import com.example.townmarket.common.util.SetHttpHeaders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(controllers = TradeController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class TradeControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  TradeService tradeService;

  @MockBean
  SetHttpHeaders httpHeaders;

  @Test
  @WithCustomMockUser
  void getPurchaseList() throws Exception {

    String json = objectMapper.writeValueAsString(PAGING_TRADE_PAGE);

    given(tradeService.getPurchaseList(any(),any())).willReturn(PAGING_TRADE_PAGE);

    //when
    ResultActions resultActions = mockMvc.perform(get("/trade/purchase")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PAGE_DTO))
            .with(csrf()))
        .andExpect(status().isOk());
    resultActions.andDo(document("tradeController/getPurchaseList",
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
            fieldWithPath("content[].userName").type(JsonFieldType.STRING).description("유저 이름"),
            fieldWithPath("content[].productName").type(JsonFieldType.STRING).description("상품이름"),
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

        )
    ));

  }

  @Test
  @WithCustomMockUser
  void getSalesList() throws Exception {

    String json = objectMapper.writeValueAsString(PAGING_TRADE_PAGE);

    given(tradeService.getSalesList(any(),any())).willReturn(PAGING_TRADE_PAGE);

    //when
    ResultActions resultActions = mockMvc.perform(get("/trade/sales")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PAGE_DTO))
            .with(csrf()))
        .andExpect(status().isOk());
    resultActions.andDo(document("tradeController/getSalesList",
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
            fieldWithPath("content[].userName").type(JsonFieldType.STRING).description("유저 이름"),
            fieldWithPath("content[].productName").type(JsonFieldType.STRING).description("상품이름"),
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

        )
    ));
  }

  @Test
  @WithCustomMockUser
  void createTrade() throws Exception {

    ResultActions resultActions = mockMvc.perform(post("/trade/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(CREATE_TRADE_DTO))
            .with(csrf()))
        .andExpect(status().isOk());
    resultActions.andDo(document("tradeController/createTrade",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("productId").type(JsonFieldType.NUMBER).description("상품 아이디"),
            fieldWithPath("buyerId").type(JsonFieldType.NUMBER).description("구매자 아이디")

        )));
  }
}
