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
import com.defect.tracker.resquest.dto.ModuleRequest;
import com.defect.tracker.service.ModuleService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;
import com.defect.tracker.rest.enums.RequestStatus;

@RestController
@CrossOrigin
public class ModuleControler {
  @Autowired
  private ModuleService moduleService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  private static final Logger logger = LoggerFactory.getLogger(ModuleControler.class);

  @PostMapping(value = EndpointURI.MODULE)
  public ResponseEntity<Object> saveModule(@RequestBody ModuleRequest moduleRequest) {
    if (moduleService.isModuleExists(moduleRequest.getName())) {
      logger.info("Module already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAlreadyExists(),
          validationFailureResponseCode.getValidationModuleAlreadyExists()));
    }
    logger.info("Module added successfully");
    moduleService.saveModule(moduleRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveModuleSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.MODULE)
  public ResponseEntity<Object> getAllModules() {
    logger.info("All module retrived successfully");
    return ResponseEntity.ok(new ContentResponse<>(Constants.MODULES, moduleService.getALLModule(),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetAllModuleSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.MODULE_BY_ID)
  public ResponseEntity<Object> getModuleById(@PathVariable Long id) {
    if (!moduleService.existByModule(id)) {
      logger.info("Module is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleNotExistsCode(),
          validationFailureResponseCode.getModuleNotExistsMessage()));
    }
    logger.info("Module retrived successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.MODULE, moduleService.getModuleById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetDesignationByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.MODULE)
  public ResponseEntity<Object> updateModule(@RequestBody ModuleRequest moduleRequest) {
    if (!moduleService.existByModule(moduleRequest.getId())) {
      logger.info("Module is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleNotExistsCode(),
          validationFailureResponseCode.getModuleNotExistsMessage()));
    }
    if (moduleService.isUpdateModuleNameExist(moduleRequest.getId(), moduleRequest.getName())) {
      logger.info("Module already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleAlreadyExists(),
          validationFailureResponseCode.getValidationModuleAlreadyExists()));
    }
    logger.info("Module update successfully");
    moduleService.saveModule(moduleRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateModuleSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.MODULE_BY_ID)
  public ResponseEntity<Object> deleteModule(@PathVariable Long id) {
    if (!moduleService.existByModule(id)) {
      logger.info("Module is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleNotExistsCode(),
          validationFailureResponseCode.getModuleNotExistsMessage()));
    }
    logger.info("Module delete successfully");
    moduleService.deleteModule(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteModuleSuccessMessage()));
  }
}
