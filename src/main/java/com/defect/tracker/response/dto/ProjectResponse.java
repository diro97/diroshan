package com.defect.tracker.response.dto;

import com.defect.tracker.entities.ProjectStatus;
import com.defect.tracker.entities.ProjectType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponse {
  private Long id;
  private String projectName;
  private String prefix;
  private String startDate;
  private String endDate;
  private Long projectTypeId;
  private ProjectType typeName;
  private Long projectStatusId;
  private ProjectStatus statusName;
  private String describtion;
}
