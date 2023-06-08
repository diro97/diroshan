package com.defect.tracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.Employee;
import com.defect.tracker.response.dto.EmployeeResponse;
import com.defect.tracker.resquest.dto.EmployeeRequest;
import com.defect.tracker.search.response.EmployeeSearch;

public interface EmployeeService {
  public void saveEmployee(EmployeeRequest employeeRequest);

  public boolean isEmployeeExistsByEmail(String email);

  public boolean isEmployeeExistsByContactNumber(String contactNumber);

  public boolean existByEmployee(Long id);

  public List<EmployeeResponse> getAllEmployee();

  public EmployeeResponse getEmployeeById(Long id);

  public List<Employee> getAllEmployeeByDesignationId(Long id);

  public boolean isUpdatedEmployeeEmailExist(Long id, String email);

  public boolean isUpdatedEmployeeContactNumberExist(Long id, String contactNumber);

  public void deleteEmployee(Long id);

  List<EmployeeResponse> multiSearchEmployee(Pageable pageable, Pagination pagination,
      EmployeeSearch employeeSearch);
}
