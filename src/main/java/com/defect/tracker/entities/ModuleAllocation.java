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
public class ModuleAllocation {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "projectAllocationId", nullable = false)
  private ProjectAllocation employee;
  @ManyToOne
  @JoinColumn(name = "module", nullable = false)
  private Module module;
  @ManyToOne
  @JoinColumn(name = "subModuleId", nullable = false)
  private SubModule subModule;
}
