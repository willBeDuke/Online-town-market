package com.example.townmarket.common.util;

import com.example.townmarket.common.dto.StatusResponse;
import com.example.townmarket.common.enums.ResponseMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HttpResponseEntity {

  public static final ResponseEntity<StatusResponse> RESPONSE_OK = ResponseEntity
      .status(HttpStatus.OK).body(StatusResponse.valueOf(
      ResponseMessages.SUCCESS));

  public static final ResponseEntity<StatusResponse> RESPONSE_CREATED = ResponseEntity.status(
      HttpStatus.CREATED).build();

  public static final ResponseEntity<StatusResponse> RESPONSE_DELETE = ResponseEntity.status(
      HttpStatus.NO_CONTENT).build();

}
