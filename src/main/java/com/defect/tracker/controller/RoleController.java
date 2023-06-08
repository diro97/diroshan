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
import com.defect.tracker.resquest.dto.RoleRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.RoleSearch;
import com.defect.tracker.service.ProjectAllocationService;
import com.defect.tracker.service.RoleService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class RoleController {
  @Autowired
  private RoleService roleService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private ProjectAllocationService projectAllocationService;
  private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

  @PostMapping(value = EndpointURI.ROLE)
  private ResponseEntity<Object> saveRole(@RequestBody RoleRequest roleRequest) {
    if (roleService.isRoleExists(roleRequest.getName())) {
      logger.info("Role already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getRoleAlreadyExists(),
          validationFailureResponseCode.getValidationRoleAlreadyExists()));
    }
    logger.info("Role added successfully");
    roleService.saveRole(roleRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveRoleSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.ROLE)
  private ResponseEntity<Object> getAllRole() {
    logger.info("All role retrieved successfully");
    return ResponseEntity.ok(new ContentResponse<>(Constants.ROLES, roleService.getAllRole(),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetAllRoleSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.ROLE_BY_ID)
  private ResponseEntity<Object> getRoleById(@PathVariable Long id) {
    if (!roleService.existByRole(id)) {
      logger.info("Role is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getRoleNotExistesCode(),
          validationFailureResponseCode.getRoleNotExistsMessage()));
    }
    logger.info("Role retrieved successfully for given id");
    return ResponseEntity.ok(new ContentResponse<>(Constants.ROLE, roleService.getRoleById(id),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getGetRoleByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.ROLE)
  public ResponseEntity<Object> updateRelease(@RequestBody RoleRequest roleRequest) {
    if (!roleService.existByRole(roleRequest.getId())) {
      logger.info("Role not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getRoleNotExistesCode(),
          validationFailureResponseCode.getRoleNotExistsMessage()));
    }
    if (roleService.isUpdatedRoleNameExist(roleRequest.getId(), roleRequest.getName())) {
      logger.info("Role already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getRoleAlreadyExists(),
          validationFailureResponseCode.getValidationRoleAlreadyExists()));
    }
    logger.info("Role updated successfully");
    roleService.saveRole(roleRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateRoleSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.ROLE_BY_ID)
  public ResponseEntity<Object> deleteRelease(@PathVariable Long id) {
    if (!roleService.existByRole(id)) {
      logger.info("Role is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getRoleNotExistesCode(),
          validationFailureResponseCode.getRoleNotExistsMessage()));
    }
    if (projectAllocationService.existsByRole(id)) {
      logger.info("It's mapped on defect can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getRoleCanNotDeleteCode(),
          validationFailureResponseCode.getRolecanNotDeleteMessage()));
    }
    logger.info("Role deleted successfully");
    roleService.deleteRole(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteRoleSuccessMessage()));
  }

  @GetMapping(EndpointURI.ROLE_SEARCH)
  public ResponseEntity<Object> multiSearchDesignation(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, RoleSearch roleSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.ROLE,
        roleService.multiSearchRole(pageable, pagination, roleSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchRoleSuccessMessage(), pagination));
  }
}
