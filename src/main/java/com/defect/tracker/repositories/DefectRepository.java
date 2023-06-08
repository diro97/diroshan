package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.defect.tracker.entities.Defect;

public interface DefectRepository extends JpaRepository<Defect, String> {
  boolean existsByReleaseId(Long releseId);

  boolean existsBySeverityId(Long severityId);

  boolean existsByTypeId(Long typeId);

  boolean existsByStatusId(Long statusId);

  boolean existsByPriorityId(Long priorityid);

  boolean existsByProjectId(Long projectId);

  boolean existsBySubModuleId(Long id);
}
