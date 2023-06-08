package com.defect.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.ProjectType;
import com.defect.tracker.entities.QProjectType;
import com.defect.tracker.repositories.ProjectTypeRepository;
import com.defect.tracker.response.dto.ProjectTypeResponse;
import com.defect.tracker.resquest.dto.ProjectTypeRequest;
import com.defect.tracker.search.response.ProjectTypeSearch;
import com.defect.tracker.service.ProjectTypeService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class ProjectTypeServiceImpl implements ProjectTypeService {
  @Autowired
  private ProjectTypeRepository projectTypeRepository;

  @Transactional
  public void saveProjectType(ProjectTypeRequest projectTypeRequest) {
    ProjectType projectType = new ProjectType();
    BeanUtils.copyProperties(projectTypeRequest, projectType);
    projectTypeRepository.save(projectType);
  }

  @Transactional
  public List<ProjectTypeResponse> getAllProjectType() {
    List<ProjectTypeResponse> projectTypeResponses = new ArrayList<>();
    List<ProjectType> projectTypes = projectTypeRepository.findAll();
    for (ProjectType projectType : projectTypes) {
      ProjectTypeResponse projectTypeResponse = new ProjectTypeResponse();
      BeanUtils.copyProperties(projectType, projectTypeResponse);
      projectTypeResponses.add(projectTypeResponse);
    }
    return projectTypeResponses;
  }

  @Override
  public boolean isProjectTypeExists(String name) {
    return projectTypeRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public ProjectTypeResponse getProjectTypeById(Long id) {
    ProjectType projectType = projectTypeRepository.findById(id).get();
    ProjectTypeResponse projectTypeResponse = new ProjectTypeResponse();
    BeanUtils.copyProperties(projectType, projectTypeResponse);
    return projectTypeResponse;
  }

  @Override
  public boolean existByProjectType(Long id) {
    return projectTypeRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedProjectTypeNameExist(Long id, String name) {
    return projectTypeRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deleteProjectType(Long id) {
    projectTypeRepository.deleteById(id);
  }

  @Transactional
  public List<ProjectTypeResponse> multiSerachProjectType(Pageable pageable, Pagination pagination,
      ProjectTypeSearch projectTypeSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QProjectType qProjectType = QProjectType.projectType;
    if (Utils.isNotNullAndEmpty(projectTypeSearch.getName())) {
      booleanBuilder.and(qProjectType.name.containsIgnoreCase(projectTypeSearch.getName()));
    }
    List<ProjectTypeResponse> projectTypeResponses = new ArrayList<>();
    Page<ProjectType> projectTypes = projectTypeRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(projectTypes.getTotalElements());
    pagination.setTotalPages(projectTypes.getTotalPages());
    for (ProjectType projectType : projectTypes.toList()) {
      ProjectTypeResponse projectTypeResponse = new ProjectTypeResponse();
      BeanUtils.copyProperties(projectType, projectTypeResponse);
      projectTypeResponses.add(projectTypeResponse);
    }
    return projectTypeResponses;
  }
}
