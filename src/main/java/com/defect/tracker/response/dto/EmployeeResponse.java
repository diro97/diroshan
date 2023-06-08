package com.defect.tracker.response.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponse {
  private Long id;
  private String firstName;
  private String lastName;
  private String gender;
  private Long designationId;
  private String designationName;
  private String email;
  private String contactNumber;
  private Double availability;
}
