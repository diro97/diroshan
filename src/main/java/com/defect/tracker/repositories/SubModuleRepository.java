package com.defect.tracker.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.defect.tracker.entities.SubModule;

public interface SubModuleRepository
    extends JpaRepository<SubModule, Long>, QuerydslPredicateExecutor<SubModule> {
  boolean existsByNameIgnoreCase(String name);

  boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

  List<SubModule> findByModuleId(Long moduleId);

  boolean existsByModuleId(Long id);
}
