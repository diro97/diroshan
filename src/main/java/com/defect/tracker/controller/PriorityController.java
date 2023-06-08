package com.defect.tracker.controller;

import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import com.defect.tracker.resquest.dto.PriorityRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.PrioritySearch;
import com.defect.tracker.service.DefectService;
import com.defect.tracker.service.PriorityService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class PriorityController {
  @Autowired
  private PriorityService priorityService;
  @Autowired
  private DefectService defectService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  private static final Logger logger = LoggerFactory.getLogger(PriorityController.class);

  @PostMapping(value = EndpointURI.PRIORITY)
  public ResponseEntity<Object> savePriority(@RequestBody PriorityRequest priorityRequest) {
    if (priorityService.isPriorityExists(priorityRequest.getName())) {
      logger.info("Priority already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getPriorityAlreadyExists(),
          validationFailureResponseCode.getValidationPriorityAlreadyExists()));
    }
    logger.info("Priority added successfully");
    priorityService.savePriority(priorityRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSavePrioritySuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PRIORITY)
  public ResponseEntity<Object> getAllPriority() {
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.PRIORITYS, priorityService.getAllPriority(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllPrioritySuccessMessage()));
  }

  @GetMapping(value = EndpointURI.PRIORITY_BY_ID)
  public ResponseEntity<Object> getPriorityById(@PathVariable Long id) {
    if (!priorityService.existsByPriority(id)) {
      logger.info("Priority is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getPriorityNotExistsCode(),
          validationFailureResponseCode.getPriorityNotExistsMessage()));
    }
    logger.info("Priority retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.PRIORITY, priorityService.getPriorityById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetPriorityByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.PRIORITY)
  public ResponseEntity<Object> updateDesignation(@RequestBody PriorityRequest priorityRequest) {
    if (!priorityService.existsByPriority(priorityRequest.getId())) {
      logger.info("Priority is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getPriorityNotExistsCode(),
          validationFailureResponseCode.getPriorityNotExistsMessage()));
    }
    if (priorityService.isUpdatedPriorityNameExist(priorityRequest.getId(),
        priorityRequest.getName())) {
      logger.info("Priority already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getPriorityAlreadyExists(),
          validationFailureResponseCode.getValidationPriorityAlreadyExists()));
    }
    logger.info("Priority updated successfully");
    priorityService.savePriority(priorityRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdatePrioritySuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.PRIORITY_BY_ID)
  public ResponseEntity<Object> deletePriority(@PathVariable Long id) {
    if (!priorityService.existsByPriority(id)) {
      logger.info("Priority is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getPriorityNotExistsCode(),
          validationFailureResponseCode.getPriorityNotExistsMessage()));
    }
    if (defectService.existsByPriority(id)) {
      logger.info("Priority is Used in Defect So can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getPriorityCanNotDeleteCode(),
          validationFailureResponseCode.getPriorityCanNotDeleteMessage()));
    }
    logger.info("Priority deleted successfully");
    priorityService.deletePriority(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeletePrioritySuccessMessage()));
  }

  @GetMapping(EndpointURI.PRIORITY_SEARCH)
  public ResponseEntity<Object> multiSearchPriority(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, PrioritySearch prioritySearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.PRIORITY,
        priorityService.multiSearchPriority(pageable, pagination, prioritySearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchPrioritySuccessMessage(), pagination));
  }
}
