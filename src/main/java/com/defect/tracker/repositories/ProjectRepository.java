package com.defect.tracker.repositories;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.defect.tracker.entities.Project;
import com.defect.tracker.response.dto.ProjectResponse;

public interface ProjectRepository
    extends JpaRepository<Project, Long>, QuerydslPredicateExecutor<Project> {

  boolean existsByTypeId(Long typeId);

  boolean existsByProjectNameIgnoreCase(String ProjectName);

  boolean existsByProjectNameIgnoreCaseAndIdNot(String ProjectName, Long id);

  boolean existsByStatusId(Long statusId);

  List<ProjectResponse> getAllProjectByTypeId(Long id);

  List<Project> findByTypeId(Long projectTypeId);
  
  public boolean existsByTypeIdAndStatusId(Long projectStatusId, Long projectTypeId);


}
