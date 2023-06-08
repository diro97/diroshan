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
import com.defect.tracker.entities.Project;
import com.defect.tracker.entities.ProjectStatus;
import com.defect.tracker.entities.ProjectType;
import com.defect.tracker.entities.QProject;
import com.defect.tracker.repositories.ProjectRepository;
import com.defect.tracker.response.dto.ProjectResponse;
import com.defect.tracker.resquest.dto.ProjectRequest;
import com.defect.tracker.search.response.ProjectSearch;
import com.defect.tracker.service.ProjectService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;


@Service
public class ProjectServiceImpl implements ProjectService {
  @Autowired
  private ProjectRepository projectRepository;

  @Transactional
  public void saveProject(ProjectRequest projectRequest) {
    Project project = new Project();
    BeanUtils.copyProperties(projectRequest, project);

    ProjectType projectType = new ProjectType();
    projectType.setId(projectRequest.getProjectTypeId());
    project.setType(projectType);

    ProjectStatus projectStatus = new ProjectStatus();
    projectStatus.setId(projectRequest.getProjectStatusId());
    project.setStatus(projectStatus);

    projectRepository.save(project);

  }

  @Transactional
  public List<ProjectResponse> getAllProject() {
    List<ProjectResponse> projectResponses = new ArrayList<>();
    List<Project> projects = projectRepository.findAll();
    for (Project project : projects) {
      ProjectResponse projectResponse = new ProjectResponse();
      BeanUtils.copyProperties(project, projectResponse);
      projectResponse.setProjectTypeId(project.getType().getId());
      projectResponse.setTypeName(project.getType());

      projectResponse.setProjectStatusId(project.getStatus().getId());
      projectResponse.setStatusName(project.getStatus());
      projectResponses.add(projectResponse);
    }
    return projectResponses;
  }

  @Override
  public boolean existsByProjectStatus(Long statusId) {
    return projectRepository.existsByStatusId(statusId);
  }


  @Override
  public boolean existByProject(Long id) {
    return projectRepository.existsById(id);
  }

  @Override
  public boolean isProjectExistsByProjectName(String projectName) {
    return projectRepository.existsByProjectNameIgnoreCase(projectName);
  }

  @Override
  public ProjectResponse getProjectById(Long id) {
    Project project = projectRepository.findById(id).get();
    ProjectResponse projectResponse = new ProjectResponse();
    BeanUtils.copyProperties(project, projectResponse);
    projectResponse.setProjectTypeId(project.getType().getId());
    projectResponse.setTypeName(project.getType());
    projectResponse.setProjectStatusId(project.getStatus().getId());
    projectResponse.setStatusName(project.getStatus());
    return projectResponse;
  }

  @Override
  public void deleteProject(Long id) {
    projectRepository.deleteById(id);

  }

  @Override
  public boolean isUpdateProjectNameExist(Long id, String projectName) {
    return projectRepository.existsByProjectNameIgnoreCaseAndIdNot(projectName, id);

  }

  @Override
  public List<ProjectResponse> findAllProjectByTypeId(Long id) {
    return projectRepository.getAllProjectByTypeId(id);
  }

  @Transactional
  public List<ProjectResponse> multiSearchProject(Pageable pageable, Pagination pagination,
      ProjectSearch projectSearch) {
    BooleanBuilder booleanBuilder=new BooleanBuilder();
    QProject qProject = QProject.project;
    if(Utils.isNotNullAndEmpty(projectSearch.getProjectName()))
    {
    booleanBuilder.and(qProject.projectName.containsIgnoreCase(projectSearch.getProjectName())); 
  }
    if(Utils.isNotNullAndEmpty(projectSearch.getPrefix()))
    {
    booleanBuilder.and(qProject.prefix.containsIgnoreCase(projectSearch.getPrefix())); 
  }
    List<ProjectResponse>projectResponses=new ArrayList<>();
    Page<Project> projects = projectRepository.findAll(booleanBuilder,pageable);
    pagination.setTotalRecords(projects.getTotalElements());
    pagination.setTotalPages(projects.getTotalPages());
    for(Project project : projects.toList()) {
      ProjectResponse projectResponse = new ProjectResponse();
      BeanUtils.copyProperties(project,projectResponse);
      projectResponses.add(projectResponse);
    }
    return projectResponses;
  }

  @Override
  public List<Project> getAllProjectByProjectTypeId(Long id) {
  
    return projectRepository.findByTypeId(id);
  }

  @Override
  public boolean existsByProjectType(Long typeId) {
    return projectRepository.existsByTypeId(typeId);
  }

  @Override
  public boolean existsByProjectTypeAndProjectStatus(Long projectStatusId, Long projectTypeId) {
    
    return projectRepository.existsByTypeIdAndStatusId(projectStatusId, projectTypeId);
  }



 
  }
