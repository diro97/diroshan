package com.defect.tracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import com.defect.tracker.resquest.dto.ProjectStatusRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.ProjectStatusSearch;
import com.defect.tracker.service.ProjectService;
import com.defect.tracker.service.ProjectStatusService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class ProjectStatusController {
  @Autowired
  private ProjectStatusService projectStatusService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private ProjectService projectService;
  private static final Logger logger = LoggerFactory.getLogger(ProjectStatusController.class);

  @PostMapping(value = EndpointURI.PROJECTSTATUS)
  public ResponseEntity<Object> saveProjectStatus(
      @RequestBody ProjectStatusRequest projectStatusRequest) {
    if (projectStatusService.isProjectStatusExists(projectStatusRequest.getName())) {
      logger.info("ProjectStatus already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusAlreadyExists(),
          validationFailureResponseCode.getValidationProjectStatusAlreadyExists()));
    }
    logger.info("ProjectStatus added successfully");
    projectStatusService.saveProjectStatus(projectStatusRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveProjectStatusSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PROJECTSTATUS)
  public ResponseEntity<Object> getAllProjectStatuses() {
    logger.info("All projectStatus retrieved successfully");
    return ResponseEntity.ok(
        new ContentResponse<>(Constants.PROJECTSTATUSES, projectStatusService.getAllProjectStatus(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllProjectStatusSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PROJECTSTATUS_BY_ID)
  public ResponseEntity<Object> getProjectStatusById(@PathVariable Long id) {
    if (!projectStatusService.existByProjectStatus(id)) {
      logger.info("ProjectStatus is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusNotExistsCode(),
          validationFailureResponseCode.getProjectStatusNotExistsMessage()));
    }
    logger.info("ProjectStatus retrieved successfully for given id");
    return ResponseEntity.ok(new ContentResponse<>(Constants.PROJECTSTATUS,
        projectStatusService.getProjectStatusById(id), RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetProjectStatusByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.PROJECTSTATUS)
  public ResponseEntity<Object> updateProjectStatus(
      @RequestBody ProjectStatusRequest projectStatusRequest) {
    if (!projectStatusService.existByProjectStatus(projectStatusRequest.getId())) {
      logger.info("ProjectStatus is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusNotExistsCode(),
          validationFailureResponseCode.getProjectStatusNotExistsMessage()));
    }
    if ((projectStatusService.isUpdatedProjectStatusNameExist(projectStatusRequest.getId(),
        projectStatusRequest.getName()))) {
      logger.info("ProjectStatus already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusAlreadyExists(),
          validationFailureResponseCode.getValidationProjectStatusAlreadyExists()));
    }
    if ((projectStatusService.isUpdatedProjectStatusColorExist(projectStatusRequest.getId(),
        projectStatusRequest.getColor()))) {
      logger.info("ProjectStatus already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusAlreadyExists(),
          validationFailureResponseCode.getValidationProjectStatusAlreadyExists()));
    }
    logger.info("ProjectStatus updated successfully");
    projectStatusService.saveProjectStatus(projectStatusRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateProjectStatusSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.PROJECTSTATUS_BY_ID)
  public ResponseEntity<Object> deleteProjectStatus(@PathVariable Long id) {
    if (!projectStatusService.existByProjectStatus(id)) {
      logger.info("ProjectStatus is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusNotExistsCode(),
          validationFailureResponseCode.getProjectStatusNotExistsMessage()));
    }
    if (projectService.existsByProjectStatus(id)) {
      logger.info("It's mapped on project. so, can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getProjectStatusCanNotDeleteCode(),
          validationFailureResponseCode.getProjectStatusCanNotDeleteMessage()));
    }
    logger.info("ProjectStatus deleted successfully");
    projectStatusService.deleteProjectStatus(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteProjectStatusSuccessMessage()));
  }

  @GetMapping(EndpointURI.PROJECTSTATUS_SEARCH)
  public ResponseEntity<Object> multiSearchProjectStatus(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, ProjectStatusSearch projectStatusSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.PROJECTSTATUS,
        projectStatusService.multiSearchProjectStatus(pageable, pagination, projectStatusSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchProjectStatusSuccessMessage(), pagination));
  }
}
