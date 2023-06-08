package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.defect.tracker.entities.ModuleAllocation;

public interface ModuleAllocationRepository extends JpaRepository<ModuleAllocation, Long> {
  boolean existsBySubModuleId(Long id);
}
