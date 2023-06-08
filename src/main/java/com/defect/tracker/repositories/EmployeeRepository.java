package com.defect.tracker.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.defect.tracker.entities.Employee;

public interface EmployeeRepository
    extends JpaRepository<Employee, Long>, QuerydslPredicateExecutor<Employee> {
  public boolean existsByEmailIgnoreCase(String email);

  public boolean existsByContactNumberIgnoreCase(String contactNumber);

  public List<Employee> findByDesignationId(Long designationId);
}
