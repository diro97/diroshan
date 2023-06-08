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
import com.defect.tracker.resquest.dto.DefectTypeRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.DefectTypeSearch;
import com.defect.tracker.service.DefectService;
import com.defect.tracker.service.DefectTypeService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class DefectTypeController {
  @Autowired
  private DefectTypeService defectTypeService;
  @Autowired
  private DefectService defectService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  private static final Logger logger = LoggerFactory.getLogger(DefectTypeController.class);

  @PostMapping(value = EndpointURI.DEFECTTYPE)
  public ResponseEntity<Object> saveDefectType(@RequestBody DefectTypeRequest defectTypeRequest) {
    if (defectTypeService.isDefectTypeExists(defectTypeRequest.getName())) {
      logger.info("DefectType already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectTypeAlreadyExists(),
          validationFailureResponseCode.getValidationDefectTypeAlreadyExists()));
    }
    logger.info("DefectType added successfully");
    defectTypeService.saveDefectType(defectTypeRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveDefectTypeSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.DEFECTTYPE)
  public ResponseEntity<Object> getAllDefectTypes() {
    logger.info("All defectType retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.DEFECTTYPES, defectTypeService.getAllDefectType(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllDefectTypeSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.DEFECTTYPE_BY_ID)
  public ResponseEntity<Object> getDefectTypeById(@PathVariable Long id) {
    if (!defectTypeService.existByDefectType(id)) {
      logger.info("DefectType is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectTypeNotExistsCode(),
          validationFailureResponseCode.getDefectTypeNotExistsMessage()));
    }
    logger.info("DefectType retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.DEFECTTYPE, defectTypeService.getDefectTypeById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetDefectTypeByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.DEFECTTYPE)
  public ResponseEntity<Object> updateDefectType(@RequestBody DefectTypeRequest defectTypeRequest) {
    if (!defectTypeService.existByDefectType(defectTypeRequest.getId())) {
      logger.info("DefectType is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectTypeNotExistsCode(),
          validationFailureResponseCode.getDefectTypeNotExistsMessage()));
    }
    if (defectTypeService.isUpdateDefectTypeNameExist(defectTypeRequest.getId(),
        defectTypeRequest.getName())) {
      logger.info("DefectType already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectTypeAlreadyExists(),
          validationFailureResponseCode.getValidationDefectTypeAlreadyExists()));
    }
    logger.info("DefectType updated successfully");
    defectTypeService.saveDefectType(defectTypeRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateDefectTypeSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.DEFECTTYPE_BY_ID)
  public ResponseEntity<Object> deleteDefectType(@PathVariable Long id) {
    if (!defectTypeService.existByDefectType(id)) {
      logger.info("DefectType is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectTypeNotExistsCode(),
          validationFailureResponseCode.getDefectTypeNotExistsMessage()));
    }
    if (defectService.existsByDefectType(id)) {
      logger.info("It's mapped on defectType can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectTypeCanNotDeleteCode(),
          validationFailureResponseCode.getDefectTypecanNotDeleteMessage()));
    }
    logger.info("Designation deleted successfully");
    defectTypeService.deleteDefectType(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteDefectTypeSuccessMessage()));
  }

  @GetMapping(EndpointURI.DEFECTTYPE_SEARCH)
  public ResponseEntity<Object> multiSearchDefectType(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, DefectTypeSearch defectTypeSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.DEFECTTYPE,
        defectTypeService.multiSearchDefectType(pageable, pagination, defectTypeSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchDefectTypeSuccessMessage(), pagination));
  }
}


