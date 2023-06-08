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
import com.defect.tracker.resquest.dto.SeverityRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.SeveritySearch;
import com.defect.tracker.service.DefectService;
import com.defect.tracker.service.SeverityService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class SeverityController {
  @Autowired
  private SeverityService severityService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private DefectService defectService;
  private static final Logger logger = LoggerFactory.getLogger(SeverityController.class);

  @PostMapping(value = EndpointURI.SEVERITY)
  public ResponseEntity<Object> saveDesignation(@RequestBody SeverityRequest severityRequest) {
    if (severityService.isSeverityExistsByName(severityRequest.getName())) {
      logger.info("Severity already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSeverityAlreadyExists(),
          validationFailureResponseCode.getValidationSeverityAlreadyExists()));
    }
    logger.info("Severity added successfully");
    severityService.saveSeverity(severityRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveSeveritySuccessMessage()));
  }

  @GetMapping(value = EndpointURI.SEVERITY)
  public ResponseEntity<Object> getAllSeverities() {
    logger.info("All Severities retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.SEVERITY, severityService.getAllSeverities(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllSeveritiesSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.SEVERITY_BY_ID)
  public ResponseEntity<Object> getSeverityById(@PathVariable Long id) {
    if (!severityService.existBySeverity(id)) {
      logger.info("Severity is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSeverityNotExistsCode(),
          validationFailureResponseCode.getSeverityNotExistsMessage()));
    }
    logger.info("Severity retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.SEVERITY, severityService.getSeverityById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetSeverityByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.SEVERITY)
  public ResponseEntity<Object> updateSeverity(@RequestBody SeverityRequest severityRequest) {
    if (!severityService.existBySeverity(severityRequest.getId())) {
      logger.info("Severity is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSeverityNotExistsCode(),
          validationFailureResponseCode.getSeverityNotExistsMessage()));
    }
    if (severityService.isUpdatedSeverityNameExist(severityRequest.getId(),
        severityRequest.getName())) {
      logger.info("Severity already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDesignationAlreadyExists(),
          validationFailureResponseCode.getValidationDesignationAlreadyExists()));
    }
    logger.info("Severity updated successfully");
    severityService.saveSeverity(severityRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateSeveritySuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.SEVERITY_BY_ID)
  public ResponseEntity<Object> deleteSeverity(@PathVariable Long id) {
    if (!severityService.existBySeverity(id)) {
      logger.info("Severity is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSeverityNotExistsCode(),
          validationFailureResponseCode.getSeverityNotExistsMessage()));
    }
    if (defectService.existsBySeverity(id)) {
      logger.info("It's mapped on defect can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSeverityCanNotDeleteCode(),
          validationFailureResponseCode.getSeverityCanNotDeleteMessage()));
    }
    logger.info("Severity deleted successfully");
    severityService.deleteSeverity(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteSeveritySuccessMessage()));
  }

  @GetMapping(EndpointURI.SEVERITY_SEARCH)
  public ResponseEntity<Object> multiSearchSeverity(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, SeveritySearch severitySearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.SEVERITY,
        severityService.multiSearchSeverity(pageable, pagination, severitySearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchSeveritySuccessMessage(), pagination));

  }
}
