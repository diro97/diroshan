package com.defect.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.defect.tracker.repositories.DefectRepository;
import com.defect.tracker.service.DefectService;

@Service
public class DefectServiceImpl implements DefectService {
  @Autowired
  private DefectRepository defectRepository;

  @Override
  public boolean existsByRelease(Long releaseId) {
    return defectRepository.existsByReleaseId(releaseId);
  }

  @Override
  public boolean existsBySeverity(Long severityId) {
    return defectRepository.existsBySeverityId(severityId);
  }

  @Override
  public boolean existsByDefectType(Long typeId) {
    return defectRepository.existsByTypeId(typeId);
  }

  @Override
  public boolean existsByDefectStatus(Long statusId) {
    return defectRepository.existsByStatusId(statusId);
  }

  @Override
  public boolean existsByPriority(Long priorityid) {
    return defectRepository.existsByPriorityId(priorityid);
  }

  @Override

  public boolean existsByProject(Long projectId) {
    return defectRepository.existsByProjectId(projectId);
  }

  public boolean existsBySubModule(Long subModuleId) {
    return defectRepository.existsBySubModuleId(subModuleId);

  }
}
