package com.defect.tracker.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubModuleResponse {
  private Long id;
  private String name;
  private Long moduleId;
  private String moduleName;
}
