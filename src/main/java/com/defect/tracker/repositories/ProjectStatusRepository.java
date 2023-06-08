package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.defect.tracker.entities.ProjectStatus;

public interface ProjectStatusRepository
    extends JpaRepository<ProjectStatus, Long>, QuerydslPredicateExecutor<ProjectStatus> {
  boolean existsByNameIgnoreCase(String name);

  boolean existsByColorIgnoreCase(String color);

  boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

  boolean existsByColorIgnoreCaseAndIdNot(String color, Long id);
}
