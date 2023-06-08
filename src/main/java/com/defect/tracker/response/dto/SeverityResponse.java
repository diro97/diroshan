package com.defect.tracker.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeverityResponse {
  private long id;
  private String name;
  private String color;
}
