package com.defect.tracker.controller;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
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
import com.defect.tracker.resquest.dto.EmployeeRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.EmployeeSearch;
import com.defect.tracker.service.DesignationService;
import com.defect.tracker.service.EmployeeService;
import com.defect.tracker.service.ProjectAllocationService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class EmployeeController {
  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private DesignationService designationService;
  @Autowired
  private ProjectAllocationService projectAllocationService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

  @PostMapping(value = EndpointURI.EMPLOYEE)
  public ResponseEntity<Object> saveEmployee(@RequestBody EmployeeRequest employeeRequest) {
    if (employeeService.isEmployeeExistsByEmail(employeeRequest.getEmail())) {
      logger.info("Employee already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getEmployeAlreadyExists(),
          validationFailureResponseCode.getValidationEmployeeAlreadyExists()));
    }
    if (employeeService.isEmployeeExistsByContactNumber(employeeRequest.getContactNumber())) {
      logger.info("Employee already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getEmployeAlreadyExists(),
          validationFailureResponseCode.getValidationEmployeeAlreadyExists()));
    }
    logger.info("Employee added sucessfully");
    employeeService.saveEmployee(employeeRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveEmployeeSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.EMPLOYEE)
  public ResponseEntity<Object> getAllEmployees() {
    logger.info("All employees retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.EMPLOYEES, employeeService.getAllEmployee(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllEmployeeSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.EMPLOYEE_BY_ID)
  public ResponseEntity<Object> getEmployeeById(@PathVariable Long id) {
    if (!employeeService.existByEmployee(id)) {
      logger.info("Employee is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getEmployeeNotExistsCode(),
          validationFailureResponseCode.getEmployeeNotExistsMessage()));
    }
    logger.info("Employee retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.EMPLOYEE, employeeService.getEmployeeById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetEmployeeByIdSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.EMPLOYEE_DESIGNATION_BY_ID)
  public ResponseEntity<Object> getEmployeeByDesignationId(@PathVariable Long id) {
    if (!designationService.existByDesignation(id)) {
      logger.info("Designation is not exist");
      return ResponseEntity.ok((new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getDesignationNotExistsCode(),
          validationFailureResponseCode.getDesignationNotExistsMessage())));
    }
    logger.info("Employee retrived successfully for given designation id");
    return ResponseEntity.ok(
        new ContentResponse<>(Constants.EMPLOYEE, employeeService.getAllEmployeeByDesignationId(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetEmployeeByIdSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.EMPLOYEE)
  public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeRequest employeeRequest) {
    if (!employeeService.existByEmployee(employeeRequest.getId())) {
      logger.info("Employee is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getEmployeeNotExistsCode(),
          validationFailureResponseCode.getEmployeeNotExistsMessage()));
    }
    if (employeeService.isUpdatedEmployeeEmailExist(employeeRequest.getId(),
        employeeRequest.getEmail())) {
      logger.info("Employee already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getEmployeAlreadyExists(),
          validationFailureResponseCode.getValidationEmployeeAlreadyExists()));
    }
    logger.info("Employee updated successfully");
    employeeService.saveEmployee(employeeRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateEmployeeSuccessMessage()));
  }

  @GetMapping(EndpointURI.EMPLOYEE_SEARCH)
  public ResponseEntity<Object> multiSearchEmployee(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, EmployeeSearch employeeSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.EMPLOYEE,
        employeeService.multiSearchEmployee(pageable, pagination, employeeSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchEmployeeSuccessMessage(), pagination));
  }

  @DeleteMapping(value = EndpointURI.EMPLOYEE_BY_ID)
  public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
    if (!employeeService.existByEmployee(id)) {
      logger.info("Employee is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getEmployeeNotExistsCode(),
          validationFailureResponseCode.getEmployeeNotExistsMessage()));
    }
    if (projectAllocationService.existsByEmployee(id)) {
      logger.info("Employee Mapped with ProjectAllocation");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getEmployeeCanNotDeleteCode(),
          validationFailureResponseCode.getEmployeeCanNotDeleteMessage()));
    }
    logger.info("Employee deleted successfully");
    employeeService.deleteEmployee(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteEmployeeSuccessMessage()));
  }
}
