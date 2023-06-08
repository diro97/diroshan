package com.defect.tracker.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.defect.tracker.common.response.BaseResponse;
import com.defect.tracker.common.response.ContentResponse;
import com.defect.tracker.common.response.PaginatedContentResponse;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.resquest.dto.ProjectRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.ProjectSearch;
import com.defect.tracker.service.DefectService;
import com.defect.tracker.service.ProjectService;
import com.defect.tracker.service.ProjectTypeService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;


@RestController
@CrossOrigin
public class ProjectController {
  @Autowired
  private ProjectService projectService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private ProjectTypeService projectTypeService;
  
  @Autowired
  private DefectService defectService;
  
  private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

  @PostMapping(value = EndpointURI.PROJECT)
  public ResponseEntity<Object> saveProject(@RequestBody ProjectRequest projectRequest) {
    if(projectService.existsByProjectTypeAndProjectStatus(projectRequest.getProjectTypeId(),projectRequest.getProjectStatusId())) {
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectAlreadyExists(),
          validationFailureResponseCode.getValidationPriorityAlreadyExists()));
    }
      
    if (projectService.isProjectExistsByProjectName(projectRequest.getProjectName())) {
      logger.info("Project already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectAlreadyExists(),
          validationFailureResponseCode.getValidationProjectAlreadyExists()));
    }
    if (projectService.existsByProjectType(projectRequest.getProjectTypeId())) {
      logger.info("projectType not exist");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeNotExistesCode(),
          validationFailureResponseCode.getProjectTypeNotExistsMessage()));
    }
    if (projectService.existsByProjectStatus(projectRequest.getProjectStatusId())) {
      logger.info("projectStatus not exist");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusNotExistsCode(),
          validationFailureResponseCode.getProjectStatusNotExistsMessage()));
    }
    
    logger.info("Project added Successfully");
    projectService.saveProject(projectRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveProjectSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PROJECT)
  public ResponseEntity<Object> getAllProject() {
    logger.info("All projects retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.PROJECTS, projectService.getAllProject(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllProjectSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PROJECT_BY_ID)
  public ResponseEntity<Object> getProjectById(@PathVariable Long id) {
    if (!projectService.existByProject(id)) {
      logger.info("Project is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectNotExistesCode(),
          validationFailureResponseCode.getProjectNotExistsMessage()));
    }
    logger.info("Project retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.PROJECT, projectService.getProjectById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetProjectByIdSuccessMessage()));
  }
  @GetMapping(value = EndpointURI.PROJECT_BY_PROJECT_TYPE_ID)
  public ResponseEntity<Object> getAllProjectByProjectTypeId(@PathVariable Long id) {
    if (!projectService.existsByProjectType(id)) {
      logger.info("projectType not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeNotExistesCode(),
          validationFailureResponseCode.getProjectTypeNotExistsMessage()));
    }
    logger.info("Project retrieved successfully for given projectType id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.PROJECTS, projectService.getAllProjectByProjectTypeId(id),
            RequestStatus.SUCCESS.getStatus(), 
            validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllProjectSuccessMessage()));
  }

//  @GetMapping(value =EndpointURI.PROJECT_BY_PROJECT_TYPE_ID)
//  public ResponseEntity<Object>getProjectByProjectTypeId(@PathVariable Long id){
//    if(!projectService.existsByProjectType(id)) {
//      logger.info("projectType not exist");
//      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
//          validationFailureResponseCode.getProjectTypeNotExistesCode(),
//          validationFailureResponseCode.getProjectNotExistsMessage()));
//    }
//    logger.info("project retrieved successfully for given projectType Id ");
//    return ResponseEntity.ok(
//        new ContentResponse<>(Constants.PROJECTS,projectService.getAllProjectByProjectTypeId(id),
//            RequestStatus.SUCCESS.getStatus(),
//            validationFailureResponseCode.getCommonSuccessCode(),
//            validationFailureResponseCode.getGetAllProjectSuccessMessage()));)
//  }

  @PutMapping(value = EndpointURI.PROJECT)
  public ResponseEntity<Object> updateProject(@RequestBody ProjectRequest projectRequest) {
    if (!projectService.existByProject(projectRequest.getId())) {
      logger.info("Project is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectNotExistesCode(),
          validationFailureResponseCode.getProjectNotExistsMessage()));
    }
    if (projectService.isUpdateProjectNameExist(projectRequest.getId(),
        projectRequest.getProjectName())) {
      logger.info("Project already exist");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
          validationFailureResponseCode.getProjectAlreadyExists(),
          validationFailureResponseCode.getUpdateProjectSuccessMessage()));
    }
    if (!projectTypeService.existByProjectType(projectRequest.getProjectTypeId())) {
      logger.info("projectType not exist");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeNotExistesCode(),
          validationFailureResponseCode.getProjectTypeNotExistsMessage()));
    }
    if (!projectService.existsByProjectStatus(projectRequest.getProjectStatusId())) {
      logger.info("projectStatus not exist");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusNotExistsCode(),
          validationFailureResponseCode.getProjectStatusNotExistsMessage()));
    }
    logger.info("project update success");
    projectService.saveProject(projectRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateProjectSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.PROJECT_BY_ID)
  public ResponseEntity<Object> deleteProject(@PathVariable Long id) {
    if (!projectService.existByProject(id)) {
      logger.info("Project is not exist");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectNotExistesCode(),
          validationFailureResponseCode.getProjectNotExistsMessage()));
    }
    if (defectService.existsByProject(id)) {
      logger.info("It's mapped on defect can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectCanNotDeletecode(),
          validationFailureResponseCode.getProjectCanNotDeleteMessage()));
    }
    logger.info("Project deleted successfully");
    projectService.deleteProject(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteProjectSuccessMessage()));
  }



  @GetMapping(EndpointURI.PROJECT_SEARCH)
  public ResponseEntity<Object> multisearchProject(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, ProjectSearch projectSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.PROJECT,
        projectService.multiSearchProject(pageable, pagination, projectSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchProjectSuccessMessage(), pagination));
  }
}
