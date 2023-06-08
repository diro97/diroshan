package com.defect.tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.defect.tracker.entities.Designation;

public interface DesignationRepository
    extends JpaRepository<Designation, Long>, QuerydslPredicateExecutor<Designation> {
  boolean existsByNameIgnoreCase(String name);

  boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}
