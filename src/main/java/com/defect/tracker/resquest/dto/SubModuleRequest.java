package com.defect.tracker.resquest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubModuleRequest {
  private Long id;
  private String name;
  private Long moduleId;
}
