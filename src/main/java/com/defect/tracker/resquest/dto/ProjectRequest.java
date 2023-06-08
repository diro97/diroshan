package com.defect.tracker.resquest.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequest {
  private Long id;
  private String projectName;
  private String prefix;
  private String startDate;
  private String endDate;
  private Long projectTypeId;
  private Long projectStatusId;
  private String describtion;
}
