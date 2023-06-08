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
import com.defect.tracker.resquest.dto.DesignationRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.DesignationSearch;
import com.defect.tracker.service.DesignationService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class DesignationController {
  @Autowired
  private DesignationService designationService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  private static final Logger logger = LoggerFactory.getLogger(DesignationController.class);

  @PostMapping(value = EndpointURI.DESIGNATION)
  public ResponseEntity<Object> saveDesignation(
      @RequestBody DesignationRequest designationRequest) {
    if (designationService.isDesignationExists(designationRequest.getName())) {
      logger.info("Designation already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDesignationAlreadyExists(), 
          validationFailureResponseCode.getValidationDesignationAlreadyExists()));
    }
    logger.info("Designation added successfully");
    designationService.saveDesignation(designationRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveDesignationSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.DESIGNATION)
  public ResponseEntity<Object> getAllDesignations() {
    logger.info("All designation retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.DESIGNATIONS, designationService.getAllDesignation(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllDesignationSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.DESIGNATION_BY_ID)
  public ResponseEntity<Object> getDesignationById(@PathVariable Long id) {
    if (!designationService.existByDesignation(id)) {
      logger.info("Designation is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDesignationNotExistsCode(),
          validationFailureResponseCode.getDesignationNotExistsMessage()));
    }
    logger.info("Designation retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.DESIGNATION, designationService.getDesignationById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetDesignationByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.DESIGNATION)
  public ResponseEntity<Object> updateDesignation(
      @RequestBody DesignationRequest designationRequest) {
    if (!designationService.existByDesignation(designationRequest.getId())) {
      logger.info("Designation is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDesignationNotExistsCode(),
          validationFailureResponseCode.getDesignationNotExistsMessage()));
    }
    if (designationService.isUpdatedDesignationNameExist(designationRequest.getId(),
        designationRequest.getName())) {
      logger.info("Designation already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDesignationAlreadyExists(),
          validationFailureResponseCode.getValidationDesignationAlreadyExists()));
    }
    logger.info("Designation updated successfully");
    designationService.saveDesignation(designationRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateDesignationSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.DESIGNATION_BY_ID)
  public ResponseEntity<Object> deleteDesignation(@PathVariable Long id) {
    if (!designationService.existByDesignation(id)) {
      logger.info("Designation is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDesignationNotExistsCode(),
          validationFailureResponseCode.getDesignationNotExistsMessage()));
    }
    logger.info("Designation deleted successfully");
    designationService.deleteDesignation(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteDesignationSuccessMessage()));
  }

  @GetMapping(EndpointURI.DESIGNATION_SEARCH)
  public ResponseEntity<Object> multiSearchDesignation(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, DesignationSearch designationSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.DESIGNATION,
        designationService.multiSearchDesignation(pageable, pagination, designationSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchDesignationSuccessMessage(), pagination));
  }
}
