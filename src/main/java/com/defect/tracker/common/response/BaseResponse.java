package com.defect.tracker.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
  private String status;
  private String statusCode;
  private String message;

  public BaseResponse(String status, String statusCode, String message) {
    this.status = status;
    this.statusCode = statusCode;
    this.message = message;
  }
}
