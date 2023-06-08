package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.defect.tracker.entities.DefectStatus;

public interface DefectStatusRepository extends JpaRepository<DefectStatus, Long> {
  boolean existsByNameIgnoreCase(String name);

  boolean existsByColorIgnoreCase(String color);

  boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

  boolean existsByColorIgnoreCaseAndIdNot(String color, Long id);
}
