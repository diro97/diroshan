package com.defect.tracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.defect.tracker.resquest.dto.ProjectTypeRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.ProjectTypeSearch;
import com.defect.tracker.service.ProjectService;
import com.defect.tracker.service.ProjectTypeService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class ProjectTypeController {
  @Autowired
  private ProjectTypeService projectTypeService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private ProjectService projectService;
  private static final Logger logger = LoggerFactory.getLogger(ProjectTypeController.class);

  @PostMapping(value = EndpointURI.PROJECT_TYPE)
  private ResponseEntity<Object> saveProjectType(
      @RequestBody ProjectTypeRequest projectTypeRequest) {
    if (projectTypeService.isProjectTypeExists(projectTypeRequest.getName())) {
      logger.info("ProjectType already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeAlreadyExists(),
          validationFailureResponseCode.getValidationProjectTypeAlreadyExists()));
    }
    logger.info("ProjectType added successfully");
    projectTypeService.saveProjectType(projectTypeRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveProjectTypeSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PROJECT_TYPE)
  private ResponseEntity<Object> getAllProjectType() {
    logger.info("All ProjectType retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.PROJECT_TYPE, projectTypeService.getAllProjectType(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllProjectTypeSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PROJECT_TYPE_BY_ID)
  private ResponseEntity<Object> getProjectTypeById(@PathVariable Long id) {
    if (!projectTypeService.existByProjectType(id)) {
      logger.info("ProjectType is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeNotExistesCode(),
          validationFailureResponseCode.getProjectTypeNotExistsMessage()));
    }
    logger.info("ProjectType retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.PROJECT_TYPE, projectTypeService.getProjectTypeById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetProjectTypeByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.PROJECT_TYPE)
  public ResponseEntity<Object> updateProjectType(
      @RequestBody ProjectTypeRequest projectTypeRequest) {
    if (!projectTypeService.existByProjectType(projectTypeRequest.getId())) {
      logger.info("ProjectType not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeNotExistesCode(),
          validationFailureResponseCode.getProjectTypeNotExistsMessage()));
    }
    if (projectTypeService.isUpdatedProjectTypeNameExist(projectTypeRequest.getId(),
        projectTypeRequest.getName())) {
      logger.info("ProjectType already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeAlreadyExists(),
          validationFailureResponseCode.getValidationProjectTypeAlreadyExists()));
    }
    logger.info("ProjectType updated successfully");
    projectTypeService.saveProjectType(projectTypeRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateProjectTypeSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.PROJECT_TYPE_BY_ID)
  public ResponseEntity<Object> deleteProjectType(@PathVariable Long id) {
    if (!projectTypeService.existByProjectType(id)) {
      logger.info("ProjectType is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeNotExistesCode(),
          validationFailureResponseCode.getProjectTypeNotExistsMessage()));
    }
    if (projectService.existsByProjectType(id)) {
      logger.info("It's mapped on defect can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectTypeCanNotDeletecode(),
          validationFailureResponseCode.getProjectTypecanNotDeleteMessage()));
    }
    logger.info("ProjectType deleted successfully");
    projectTypeService.deleteProjectType(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteProjectTypeSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PROJECT_TYPE_SEARCH)
  public ResponseEntity<Object> multiSearchProjectType(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, ProjectTypeSearch projectTypeSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.PROJECT_TYPE,
        projectTypeService.multiSerachProjectType(pageable, pagination, projectTypeSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchProjectTypeSuccessMessage(), pagination));
  }
}
