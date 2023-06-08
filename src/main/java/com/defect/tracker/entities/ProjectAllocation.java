package com.defect.tracker.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProjectAllocation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;
  private String contribution;
  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;
  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;
}
