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
import com.defect.tracker.entities.SubModule;
import com.defect.tracker.resquest.dto.SubModuleRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.SubModuleSearch;
import com.defect.tracker.service.DefectService;
import com.defect.tracker.service.ModuleAllocationService;
import com.defect.tracker.service.ModuleService;
import com.defect.tracker.service.SubModuleService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class SubModuleController {
  @Autowired
  private SubModuleService subModuleService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private ModuleAllocationService moduleAllocationService;
  @Autowired
  private DefectService defectService;
  @Autowired
  private ModuleService moduleService;
  private static final Logger logger = LoggerFactory.getLogger(SubModule.class);

  @PostMapping(value = EndpointURI.SUBMODULE)
  public ResponseEntity<Object> saveSubModule(@RequestBody SubModuleRequest subModuleRequest) {
    if (subModuleService.isSubModuleExists(subModuleRequest.getName())) {
      logger.info("SubModule already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSubModuleAlreadyExists(),
          validationFailureResponseCode.getValidationSubmoduleAlreadyExists()));
    }
    if (!moduleService.existByModule(subModuleRequest.getModuleId())) {
      logger.info("Module not Extists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleNotExistsCode(),
          validationFailureResponseCode.getModuleNotExistsMessage()));
    }
    logger.info("SubModule Added successfully");
    subModuleService.saveSubModule(subModuleRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveSubmoduleSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.SUBMODULE)
  public ResponseEntity<Object> getAllSubModule() {
    logger.info("All SubModule retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.SUBMODULES, subModuleService.getAllSubModule(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllSubmoduleSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.SUBMODULE_BY_ID)
  public ResponseEntity<Object> getSubModuleById(@PathVariable Long id) {
    if (!subModuleService.existsBySubModule(id)) {
      logger.info("SubModule is Not Exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSubModuleNotExistsCode(),
          validationFailureResponseCode.getSubmoduleNotExistsMessage()));
    }
    logger.info("SubModule retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.SUBMODULE, subModuleService.getSubModuleById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetSubmoduleByIdSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.SUBMODULE_BY_MODULEID)
  public ResponseEntity<Object> getSubModuleByModuleId(@PathVariable Long id) {
    if (!subModuleService.existsByModuleId(id)) {
      logger.info("Module Not Exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleNotExistsCode(),
          validationFailureResponseCode.getModuleNotExistsMessage()));
    }
    logger.info("SubModule retrieved successfully for given Module Id");
    return ResponseEntity.ok(
        new ContentResponse<>(Constants.SUBMODULES, subModuleService.getAllSubModuleByModuleId(id),
            RequestStatus.SUCCESS.getStatus(), 
            validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllSubmoduleSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.SUBMODULE)
  public ResponseEntity<Object> updateSubModule(@RequestBody SubModuleRequest subModuleRequest) {
    if (!subModuleService.existsBySubModule(subModuleRequest.getId())) {
      logger.info("SubModule is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSubModuleNotExistsCode(),
          validationFailureResponseCode.getSubmoduleNotExistsMessage()));
    }
    if (!moduleService.existByModule(subModuleRequest.getModuleId())) {
      logger.info("Module not Extists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getModuleNotExistsCode(),
          validationFailureResponseCode.getModuleNotExistsMessage()));
    }
    if (subModuleService.isUpdatedSubModuleNameExist(subModuleRequest.getId(),
        subModuleRequest.getName())) {
      logger.info("SubMoodule already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSubModuleAlreadyExists(),
          validationFailureResponseCode.getValidationSubmoduleAlreadyExists()));
    }
    logger.info("SubModule updated successfully");
    subModuleService.saveSubModule(subModuleRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateSubmoduleSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.SUBMODULE_BY_ID)
  public ResponseEntity<Object> deleteSubModule(@PathVariable Long id) {
    if (!subModuleService.existsBySubModule(id)) {
      logger.info("SubModule is Not Exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSubModuleNotExistsCode(),
          validationFailureResponseCode.getSubmoduleNotExistsMessage()));
    }
    if (moduleAllocationService.existsBySubModule(id) || defectService.existsBySubModule(id)) {
      logger.info("It's mapped on Defect and ModuleAllocation So we can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getSubModuleCanNotDeletecode(),
          validationFailureResponseCode.getSubModulecanNotDeleteMessage()));
    }
    logger.info("SubModule deleted successfully");
    subModuleService.deleteSubModule(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteSubmoduleSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.SUBMODULE_SEARCH)
  public ResponseEntity<Object> multiSearchSubModule(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, SubModuleSearch subModuleSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.SUBMODULE,
        subModuleService.multiSearchSubModule(pageable, pagination, subModuleSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchSubModuleSuccessMessage()));
  }
}
