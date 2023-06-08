package com.defect.tracker.service;

public interface ProjectAllocationService {
  public boolean existsByEmployee(Long employeeid);

  public boolean existsByRole(Long roleId);
}
