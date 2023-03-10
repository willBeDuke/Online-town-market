package com.example.townmarket.common.domain.product.controller;


import static com.example.townmarket.fixture.ProductFixture.PAGING_PRODUCT_RESPONSE;
import static com.example.townmarket.fixture.ProductFixture.PRODUCT_ID;
import static com.example.townmarket.fixture.ProductFixture.PRODUCT_REQUEST_DTO;
import static com.example.townmarket.fixture.ProductFixture.PRODUCT_RESPONSE_DTO;
import static com.example.townmarket.fixture.UtilFixture.PAGE_DTO;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentRequest;
import static com.example.townmarket.restdocs.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
import com.example.townmarket.common.domain.product.dto.PagingProductResponse;
import com.example.townmarket.common.domain.product.dto.ProductRequestDto;
import com.example.townmarket.common.domain.product.entity.Product.ProductCategory;
import com.example.townmarket.common.domain.product.entity.Product.ProductEnum;
import com.example.townmarket.common.domain.product.entity.Product.ProductStatus;
import com.example.townmarket.common.domain.product.service.ProductService;
import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.enums.ResponseMessages;
import com.example.townmarket.common.util.SetHttpHeaders;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(controllers = ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ProductControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  ProductService productService;

  @MockBean
  SetHttpHeaders setHttpHeaders;


  @Test
  @WithCustomMockUser
  void addProduct() throws Exception {

    ResultActions resultActions = mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(PRODUCT_REQUEST_DTO))
                .with(csrf()))
        .andExpect(status().isOk());

    resultActions.andDo(document("productController/addProduct",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("productName").description(JsonFieldType.STRING).description("상품명"),
            fieldWithPath("productPrice").description(JsonFieldType.NUMBER).description("상품가격"),
            fieldWithPath("productStatus").description(JsonFieldType.OBJECT).description("상품상태"),
            fieldWithPath("productImg").description(JsonFieldType.STRING).description("상품이미지"),
            fieldWithPath("productCategory").description(JsonFieldType.OBJECT)
                .description("상품 카테고리"),
            fieldWithPath("productContents").description(JsonFieldType.STRING).description("상품 설명"),
            fieldWithPath("productEnum").description(JsonFieldType.OBJECT).description("상품 거래 상태")
        )
    ));
  }

  @Test
  @WithCustomMockUser
  void getProduct() throws Exception {

    given(productService.getProduct(PRODUCT_ID)).willReturn(PRODUCT_RESPONSE_DTO);

    ResultActions resultActions = mockMvc.perform(get("/products/{productId}", PRODUCT_ID)
        .with(csrf()));

    resultActions.andExpect(status().isOk());
    resultActions.andDo(document("productController/getProductId",
        getDocumentRequest(),
        getDocumentResponse(),
        responseFields(
            fieldWithPath("createdAt").description(JsonFieldType.OBJECT).description("생성일자"),
            fieldWithPath("modifiedAt").description(JsonFieldType.OBJECT).description("수정일자"),
            fieldWithPath("productId").description(JsonFieldType.NUMBER).description("상품명"),
            fieldWithPath("productName").description(JsonFieldType.STRING).description("상품명"),
            fieldWithPath("viewCount").description(JsonFieldType.NUMBER).description("조회수"),
            fieldWithPath("productImg").description(JsonFieldType.STRING).description("상품이미지"),

            fieldWithPath("productPrice").description(JsonFieldType.NUMBER).description("상품가격"),
            fieldWithPath("productStatus").description(JsonFieldType.OBJECT).description("상품상태"),
            fieldWithPath("productCategory").description(JsonFieldType.OBJECT)
                .description("상품 카테고리"),
            fieldWithPath("productContents").description(JsonFieldType.STRING).description("상품 설명"),
            fieldWithPath("viewCount").description(JsonFieldType.NUMBER).description("조회수"),
            fieldWithPath("sellerId").description(JsonFieldType.NUMBER).description("판매자 아이디"),
            fieldWithPath("productEnum").description(JsonFieldType.OBJECT).description("상품 거래 상태"),
            fieldWithPath("nickName").description(JsonFieldType.STRING).description("판매자 닉네임"),
            fieldWithPath("img").description(JsonFieldType.STRING).description("판매자 프로필 이미지"),
            fieldWithPath("region").description(JsonFieldType.STRING).description("판매자 지역"),
            fieldWithPath("userGrade").description(JsonFieldType.STRING).description("판매자 평점"),
            fieldWithPath("interest").description(JsonFieldType.STRING).description("관심")
        )));
  }

  @Test
  @WithCustomMockUser
  void getProducts() throws Exception {

    Page<PagingProductResponse> productResponseDtos = new PageImpl<>(
        Collections.singletonList(PAGING_PRODUCT_RESPONSE), PAGE_DTO.toPageable(), 1);

    String json = objectMapper.writeValueAsString(productResponseDtos);

    given(productService.getProducts(any())).willReturn(productResponseDtos);

    ResultActions resultActions = mockMvc.perform(get("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(PAGE_DTO))
            .with(csrf()))
        .andExpect(status().isOk());

    resultActions.andDo(document("productController/getProducts",
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

            fieldWithPath("content[].productId").type(JsonFieldType.NUMBER).description("상품 아이디"),
            fieldWithPath("content[].productName").type(JsonFieldType.STRING).description("상품 이름"),
            fieldWithPath("content[].productPrice").type(JsonFieldType.NUMBER).description("가격"),
            fieldWithPath("content[].productImg").type(JsonFieldType.STRING).description("상품 이미지"),

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
  void update() throws Exception {
    Long productId = 1L;
    ProductRequestDto productRequestDto = ProductRequestDto.builder()
        .productName("productName")
        .productPrice(1000)
        .productStatus(ProductStatus.S)
        .productCategory(ProductCategory.CAR)
        .productEnum(ProductEnum.나눔)
        .build();
    StatusResponse statusResponse = StatusResponse.valueOf(ResponseMessages.SUCCESS);

    ResultActions resultActions = mockMvc.perform(
            put("/products/update/{productId}", productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(productRequestDto))
                .with(csrf()))
        .andExpect(status().isOk());

    resultActions.andDo(document("productController/update",
        getDocumentRequest(),
        getDocumentResponse(),
        requestFields(
            fieldWithPath("productName").description(JsonFieldType.STRING).description("상품명"),
            fieldWithPath("productPrice").description(JsonFieldType.NUMBER).description("상품가격"),
            fieldWithPath("productStatus").description(JsonFieldType.OBJECT).description("상품상태"),
            fieldWithPath("productImg").description(JsonFieldType.STRING).description("상품이미지"),

            fieldWithPath("productCategory").description(JsonFieldType.OBJECT)
                .description("상품 카테고리"),
            fieldWithPath("productEnum").description(JsonFieldType.OBJECT).description("거래 상태")
                .description("상품 카테고리"),
            fieldWithPath("productEnum").description(JsonFieldType.OBJECT).description("상품 거래 상태"),
            fieldWithPath("productContents").description(JsonFieldType.OBJECT).description("상품 설명")
        ),
        responseFields(
            fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 반환 코드"),
            fieldWithPath("message").type(JsonFieldType.STRING).description("상태 메시지")
        )
    ));

  }

  @Test
  @WithCustomMockUser
  void deleteProduct() throws Exception {
    Long productId = 1L;
    ResultActions resultActions = mockMvc.perform(
            delete("/products/{productId}", productId).with(csrf()))
        .andExpect(status().isNoContent());
    StatusResponse statusResponse = StatusResponse.valueOf(ResponseMessages.DELETE_SUCCESS);
    resultActions.andDo(document("productController/deleteProduct",
        getDocumentRequest(),
        getDocumentResponse()

    ));
  }
}
