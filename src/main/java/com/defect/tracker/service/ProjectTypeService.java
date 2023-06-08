package com.defect.tracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.response.dto.ProjectTypeResponse;
import com.defect.tracker.resquest.dto.ProjectTypeRequest;
import com.defect.tracker.search.response.ProjectTypeSearch;

public interface ProjectTypeService {
  public void saveProjectType(ProjectTypeRequest projectTypeRequest);

  public List<ProjectTypeResponse> getAllProjectType();

  public boolean isProjectTypeExists(String name);

  public ProjectTypeResponse getProjectTypeById(Long id);

  public boolean existByProjectType(Long id);

  public boolean isUpdatedProjectTypeNameExist(Long id, String name);

  public void deleteProjectType(Long id);

  public List<ProjectTypeResponse> multiSerachProjectType(Pageable pageable, Pagination pagination,
      ProjectTypeSearch projectTypeSearch);
}
