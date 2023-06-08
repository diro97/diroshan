package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.defect.tracker.entities.ProjectType;

public interface ProjectTypeRepository
    extends JpaRepository<ProjectType, Long>, QuerydslPredicateExecutor<ProjectType> {
  boolean existsByNameIgnoreCase(String name);

  boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
