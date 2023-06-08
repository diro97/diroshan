package com.defect.tracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.defect.tracker.common.response.BaseResponse;
import com.defect.tracker.common.response.ContentResponse;
import com.defect.tracker.resquest.dto.DefectStatusRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.service.DefectService;
import com.defect.tracker.service.DefectStatusService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class DefectStatusController {
  @Autowired
  private DefectStatusService defectStatusService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private DefectService defectService;
  private static final Logger logger = LoggerFactory.getLogger(DefectStatusController.class);

  @PostMapping(value = EndpointURI.DEFECTSTATUS)
  public ResponseEntity<Object> saveDefectStatus(
      @RequestBody DefectStatusRequest defectStatusRequest) {
    if (defectStatusService.isDefectStatusExistByName(defectStatusRequest.getName())) {
      logger.info("DefectStatus already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectstatusAlreadyExists(),
          validationFailureResponseCode.getValidationDefectStatusAlreadyExists()));
    }
    logger.info("DefectStatus added successfully");
    defectStatusService.saveDefectStatus(defectStatusRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveDefectStatusSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.DEFECTSTATUS)
  public ResponseEntity<Object> getAllDefectStatus() {
    logger.info("All defectstatus retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.DEFECTSTATUS, defectStatusService.getAllDefectStatus(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllDefectStatusSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.DEFECTSTATUS_BY_ID)
  public ResponseEntity<Object> getDefectStatusById(@PathVariable Long id) {
    if (!defectStatusService.existsByDefectStatus(id)) {
      logger.info("DefectStatus is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectstatusNotExistscode(),
          validationFailureResponseCode.getDefectstatusNotExistsMessage()));
    }
    logger.info("DefectStatus retrieved successfully for given id");
    return ResponseEntity.ok(
        new ContentResponse<>(Constants.DEFECTSTATUS, defectStatusService.getDefectStatusById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllDefectStatusSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.DEFECTSTATUS)
  public ResponseEntity<Object> updateDefectStatus(
      @RequestBody DefectStatusRequest defectStatusRequest) {
    if (!(defectStatusService.existsByDefectStatus(defectStatusRequest.getId()))) {
      logger.info("DefectStatus is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectstatusNotExistscode(),
          validationFailureResponseCode.getDefectstatusNotExistsMessage()));
    }
    if (defectStatusService.isUpdatedDefectStatusNameExist(defectStatusRequest.getId(),
        defectStatusRequest.getName())) {
      logger.info("DefectStatus already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectstatusAlreadyExists(),
          validationFailureResponseCode.getValidationDefectStatusAlreadyExists()));
    }
    logger.info("DefectStatusService updated Successfully");
    defectStatusService.saveDefectStatus(defectStatusRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateDefectStatusSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.DEFECTSTATUS_BY_ID)
  public ResponseEntity<Object> deleteDefectStatus(@PathVariable Long id) {
    if (!defectStatusService.existsByDefectStatus(id)) {
      logger.info("DefectStatus is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectstatusNotExistscode(),
          validationFailureResponseCode.getDefectstatusNotExistsMessage()));
    }
    if (defectService.existsByDefectStatus(id)) {
      logger.info("This is mapped with Defect");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDefectstatuscanNotDeleteCode(),
          validationFailureResponseCode.getDefectStatusCanNotDeleteMessage()));
    }
    logger.info("DefectStatus deleted successfully");
    defectStatusService.deleteDefectStatus(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteDefectStatusSuccessMessage()));
  }
}
