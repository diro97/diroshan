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
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String projectName;
  private String prefix;
  private String startDate;
  private String endDate;
  @ManyToOne
  @JoinColumn(name = "projectTypeId", nullable = false)
  private ProjectType type;
  @ManyToOne
  @JoinColumn(name = "projectStatusId", nullable = false)
  private ProjectStatus status;
  private String describtion;
}
