package com.defect.tracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.response.dto.ProjectStatusResponse;
import com.defect.tracker.resquest.dto.ProjectStatusRequest;
import com.defect.tracker.search.response.ProjectStatusSearch;

public interface ProjectStatusService {
  public void saveProjectStatus(ProjectStatusRequest projectStatusRequest);

  public List<ProjectStatusResponse> getAllProjectStatus();

  public boolean isProjectStatusExists(String name);

  public ProjectStatusResponse getProjectStatusById(Long id);

  public boolean existByProjectStatus(Long id);

  public boolean isUpdatedProjectStatusNameExist(Long id, String name);

  public boolean isUpdatedProjectStatusColorExist(Long id, String color);

  public void deleteProjectStatus(Long id);

  public List<ProjectStatusResponse> multiSearchProjectStatus(Pageable pageable,
      Pagination pagination, ProjectStatusSearch projectStatusSearch);
}
