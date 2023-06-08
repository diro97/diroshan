package com.defect.tracker.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Defect {
  @Id
  private String code;
  private String stepToRecreate;
  private String describtion;
  @ManyToOne
  @JoinColumn(name = "releaseId", insertable = false, updatable = false)
  private Release release;
  @ManyToOne
  @JoinColumn(name = "releaseId", insertable = false, updatable = false)
  private Release fixedIn;
  @ManyToOne
  @JoinColumn(name = "defectTypeId", nullable = false)
  private DefectType type;
  @ManyToOne
  @JoinColumn(name = "severityId", nullable = false)
  private Severity severity;
  @ManyToOne
  @JoinColumn(name = "priorityId", nullable = false)
  private Priority priority;
  @ManyToOne
  @JoinColumn(name = "defectStatusId", nullable = false)
  private DefectStatus status;
  @ManyToOne
  @JoinColumn(name = "projectAllocationId", insertable = false, updatable = false)
  private ProjectAllocation assignTo;
  @ManyToOne
  @JoinColumn(name = "projectAllocationId", insertable = false, updatable = false)
  private ProjectAllocation foundBy;
  @ManyToOne
  @JoinColumn(name = "projectId", nullable = false)
  private Project project;
  @ManyToOne
  @JoinColumn(name = "moduleId", nullable = false)
  private Module module;
  @ManyToOne
  @JoinColumn(name = "subModuleId", nullable = false)
  private SubModule subModule;
}
