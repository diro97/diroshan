package com.defect.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.Designation;
import com.defect.tracker.entities.Employee;
import com.defect.tracker.entities.QDesignation;
import com.defect.tracker.entities.QEmployee;
import com.defect.tracker.repositories.EmployeeRepository;
import com.defect.tracker.response.dto.EmployeeResponse;
import com.defect.tracker.resquest.dto.EmployeeRequest;
import com.defect.tracker.search.response.EmployeeSearch;
import com.defect.tracker.service.EmployeeService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;



@Service
public class EmployeeServiceImpl implements EmployeeService {
  @Autowired
  private EmployeeRepository employeeRepository;

  @Transactional
  public void saveEmployee(EmployeeRequest employeeRequest) {
    Employee employee = new Employee();
    BeanUtils.copyProperties(employeeRequest, employee);
    Designation designation = new Designation();
    designation.setId(employeeRequest.getDesignationId());
    employee.setDesignation(designation);
    employeeRepository.save(employee);
  }

  @Override
  public boolean isEmployeeExistsByEmail(String email) {
    return employeeRepository.existsByEmailIgnoreCase(email);
  }

  @Override
  public boolean isEmployeeExistsByContactNumber(String contactNumber) {
    return employeeRepository.existsByContactNumberIgnoreCase(contactNumber);
  }

  @Override
  public boolean existByEmployee(Long id) {
    return employeeRepository.existsById(id);
  }

  @Transactional
  public List<EmployeeResponse> getAllEmployee() {
    List<EmployeeResponse> employeeResponses = new ArrayList<>();
    List<Employee> employees = employeeRepository.findAll();
    for (Employee employee : employees) {
      EmployeeResponse employeeResponse = new EmployeeResponse();
      BeanUtils.copyProperties(employee, employeeResponse);
      employeeResponse.setDesignationId(employee.getDesignation().getId());
      employeeResponse.setDesignationName(employee.getDesignation().getName());
      employeeResponses.add(employeeResponse);
    }
    return employeeResponses;
  }

  @Transactional
  public EmployeeResponse getEmployeeById(Long id) {

    Employee employee = employeeRepository.findById(id).get();
    EmployeeResponse employeeResponse = new EmployeeResponse();
    BeanUtils.copyProperties(employee, employeeResponse);
    employeeResponse.setDesignationId(employee.getDesignation().getId());
    employeeResponse.setDesignationName(employee.getDesignation().getName());

    return employeeResponse;
  }

  @Override
  public List<Employee> getAllEmployeeByDesignationId(Long id) {
    return employeeRepository.findByDesignationId(id);
  }

  @Override
  public boolean isUpdatedEmployeeEmailExist(Long id, String email) {
    return employeeRepository.existsByEmailIgnoreCase(email);
  }

  @Override
  public boolean isUpdatedEmployeeContactNumberExist(Long id, String contactNumber) {
    return employeeRepository.existsByContactNumberIgnoreCase(contactNumber);
  }

  @Override
  public void deleteEmployee(Long id) {
    employeeRepository.deleteById(id);

  }

  @Transactional
  public List<EmployeeResponse> multiSearchEmployee(Pageable pageable, Pagination pagination,
      EmployeeSearch employeeSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QEmployee qEmployee = QEmployee.employee;


    if (Utils.isNotNullAndEmpty(employeeSearch.getFirstName())) {
      booleanBuilder.and(qEmployee.firstName.containsIgnoreCase(employeeSearch.getFirstName()));
    }
    if (Utils.isNotNullAndEmpty(employeeSearch.getLastName())) {
      booleanBuilder.and(qEmployee.lastName.containsIgnoreCase(employeeSearch.getLastName()));
    }
    if (Utils.isNotNullAndEmpty(employeeSearch.getContactNumber())) {
      booleanBuilder
          .and(qEmployee.contactNumber.containsIgnoreCase(employeeSearch.getContactNumber()));
    }
    if (Utils.isNotNullAndEmpty(employeeSearch.getEmail())) {
      booleanBuilder.and(qEmployee.email.containsIgnoreCase(employeeSearch.getEmail()));
    }
    if (Utils.isNotNullAndEmpty(employeeSearch.getDesignation())) {
      QDesignation qDesignation = QDesignation.designation;
      booleanBuilder.and(qDesignation.name.containsIgnoreCase(employeeSearch.getDesignation()));
    }


    List<EmployeeResponse> employeeResponses = new ArrayList<>();
    Page<Employee> employees = employeeRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(employees.getTotalElements());
    pagination.setTotalPages(employees.getTotalPages());
    for (Employee employee : employees.toList()) {
      EmployeeResponse employeeResponse = new EmployeeResponse();
      BeanUtils.copyProperties(employee, employeeResponse);
      employeeResponse.setDesignationId(employee.getDesignation().getId());
      employeeResponse.setDesignationName(employee.getDesignation().getName());
      employeeResponses.add(employeeResponse);
    }
    return employeeResponses;
  }

}
