package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.defect.tracker.entities.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {
  boolean existsByNameIgnoreCase(String name);

  Boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
