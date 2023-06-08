package com.defect.tracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.response.dto.RoleResponse;
import com.defect.tracker.resquest.dto.RoleRequest;
import com.defect.tracker.search.response.RoleSearch;

public interface RoleService {
  public void saveRole(RoleRequest roleRequest);

  public List<RoleResponse> getAllRole();

  public boolean isRoleExists(String name);

  public RoleResponse getRoleById(Long id);

  public boolean existByRole(Long id);

  public boolean isUpdatedRoleNameExist(Long id, String name);

  public void deleteRole(Long id);

  public List<RoleResponse> multiSearchRole(Pageable pageable, Pagination pagination,
      RoleSearch roleSearch);
}
