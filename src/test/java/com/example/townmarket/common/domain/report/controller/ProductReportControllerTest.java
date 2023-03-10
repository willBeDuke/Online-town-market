package com.example.townmarket.common.domain.report.controller;

import static com.example.townmarket.fixture.ReportFixture.PRODUCT_REPORT_REQUEST_DTO;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentRequest;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.townmarket.annotation.WithCustomMockUser;
import com.example.townmarket.common.domain.report.sevice.ProductReportServiceImpl;
import com.example.townmarket.common.util.SetHttpHeaders;
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
@WebMvcTest(controllers = ProductReportController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ProductReportControllerTest {

  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;
  @MockBean
  ProductReportServiceImpl productReportService;

  @MockBean
  SetHttpHeaders httpHeaders;


  @Test
  @WithCustomMockUser
  void reportProduct() throws Exception {
    ResultActions resultActions = mockMvc.perform(post("/report/product")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PRODUCT_REPORT_REQUEST_DTO))
            .with(csrf()))
        .andExpect(status().isOk());
    resultActions.andDo(document("reportController/reportProduct",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("productId").type(JsonFieldType.NUMBER).description("상품 아이디"),
            fieldWithPath("reportEnum").type(JsonFieldType.STRING).description("신고 사유"),
            fieldWithPath("reason").type(JsonFieldType.STRING).description("신고 상세 이유")
        )));
  }
}
