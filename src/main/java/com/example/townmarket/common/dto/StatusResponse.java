package com.example.townmarket.common.dto;

import com.example.townmarket.common.enums.ResponseMessages;
import lombok.Getter;

@Getter
public class StatusResponse {

  private int statusCode;
  private String message;

  public static StatusResponse valueOf(ResponseMessages responseMessages) {
    return new StatusResponse(responseMessages.getStatusCode(), responseMessages.getMessage());
  }

  public StatusResponse(int statusCode, String message) {
    this.statusCode = statusCode;
    this.message = message;
  }
}
