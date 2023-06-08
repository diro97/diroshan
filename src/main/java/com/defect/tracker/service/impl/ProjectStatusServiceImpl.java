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
import com.defect.tracker.entities.ProjectStatus;
import com.defect.tracker.entities.QProjectStatus;
import com.defect.tracker.repositories.ProjectStatusRepository;
import com.defect.tracker.response.dto.ProjectStatusResponse;
import com.defect.tracker.resquest.dto.ProjectStatusRequest;
import com.defect.tracker.search.response.ProjectStatusSearch;
import com.defect.tracker.service.ProjectStatusService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class ProjectStatusServiceImpl implements ProjectStatusService {
  @Autowired
  private ProjectStatusRepository projectStatusRepository;

  @Transactional
  public void saveProjectStatus(ProjectStatusRequest projectStatusRequest) {
    ProjectStatus projectStatus = new ProjectStatus();
    BeanUtils.copyProperties(projectStatusRequest, projectStatus);
    projectStatusRepository.save(projectStatus);
  }

  @Transactional
  public List<ProjectStatusResponse> getAllProjectStatus() {
    List<ProjectStatusResponse> projectStatusResponses = new ArrayList<>();
    List<ProjectStatus> projectStatuses = projectStatusRepository.findAll();

    for (ProjectStatus projectStatus : projectStatuses) {
      ProjectStatusResponse projectStatusResponse = new ProjectStatusResponse();
      BeanUtils.copyProperties(projectStatus, projectStatusResponse);
      projectStatusResponses.add(projectStatusResponse);
    }
    return projectStatusResponses;
  }

  @Override
  public boolean isProjectStatusExists(String name) {
    return projectStatusRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public ProjectStatusResponse getProjectStatusById(Long id) {
    ProjectStatus projectStatus = projectStatusRepository.findById(id).get();
    ProjectStatusResponse projectStatusResponse = new ProjectStatusResponse();
    BeanUtils.copyProperties(projectStatus, projectStatusResponse);
    return projectStatusResponse;
  }

  @Override
  public boolean existByProjectStatus(Long id) {
    return projectStatusRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedProjectStatusNameExist(Long id, String name) {
    return projectStatusRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public boolean isUpdatedProjectStatusColorExist(Long id, String color) {
    return projectStatusRepository.existsByColorIgnoreCaseAndIdNot(color, id);
  }

  @Override
  public void deleteProjectStatus(Long id) {
    projectStatusRepository.deleteById(id);
  }

  @Transactional
  public List<ProjectStatusResponse> multiSearchProjectStatus(Pageable pageable,
      Pagination pagination, ProjectStatusSearch projectStatusSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QProjectStatus qProjectStatus = QProjectStatus.projectStatus;
    if (Utils.isNotNullAndEmpty(projectStatusSearch.getName())) {
      booleanBuilder.and(qProjectStatus.name.containsIgnoreCase(projectStatusSearch.getName()));
    }

    List<ProjectStatusResponse> projectStatusResponses = new ArrayList<>();
    Page<ProjectStatus> projectStatuses = projectStatusRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(projectStatuses.getTotalElements());
    pagination.setTotalPages(projectStatuses.getTotalPages());
    for (ProjectStatus projectStatus : projectStatuses.toList()) {
      ProjectStatusResponse projectStatusResponse = new ProjectStatusResponse();
      BeanUtils.copyProperties(projectStatus, projectStatusResponse);
      projectStatusResponses.add(projectStatusResponse);
    }
    return projectStatusResponses;
  }
}
