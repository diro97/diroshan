package com.defect.tracker.rest.enums;

import lombok.Getter;

@Getter
public enum RequestStatus {
  SUCCESS("success"), FAILURE("failure"), UNKNOWN("unknown"), ERROR("Error"), WARNING("warning");

  private String status;

  private RequestStatus(String status) {
    this.status = status;
  }

  public static RequestStatus getByStatus(String status) {
    for (RequestStatus requestStatus : values()) {
      if (requestStatus.getStatus().equals(status)) {
        return requestStatus;
      }
    }
    throw new AssertionError("Request status not found for given status [status: " + status + "]");
  }
}
