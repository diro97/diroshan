package com.defect.tracker.resquest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefectStatusRequest {
  private Long id;
  private String name;
  private String color;
}
