package com.defect.tracker.search.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSearch {
  private String firstName;
  private String lastName;
  private String email;
  private String contactNumber;
  private String designation;
}
