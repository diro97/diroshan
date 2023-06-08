package com.defect.tracker.resquest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectStatusRequest {
  private Long id;
  private String name;
  private String color;
}
