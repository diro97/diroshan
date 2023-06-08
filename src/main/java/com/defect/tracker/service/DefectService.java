package com.defect.tracker.service;

public interface DefectService {
  public boolean existsByRelease(Long releaseId);

  public boolean existsBySeverity(Long severityId);

  public boolean existsByDefectType(Long typeId);

  public boolean existsByDefectStatus(Long statusId);

  public boolean existsByPriority(Long priorityid);

  public boolean existsByProject(Long projectId);

  public boolean existsBySubModule(Long subModuleId);

}
