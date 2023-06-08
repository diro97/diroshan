package com.defect.tracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.Project;
import com.defect.tracker.response.dto.ProjectResponse;
import com.defect.tracker.resquest.dto.ProjectRequest;
import com.defect.tracker.search.response.ProjectSearch;

public interface ProjectService {
  public void saveProject(ProjectRequest projectRequest);

  public List<ProjectResponse> getAllProject();

  public boolean isProjectExistsByProjectName(String projectName);

  public ProjectResponse getProjectById(Long id);

  public boolean existByProject(Long id);

  public boolean isUpdateProjectNameExist(Long id, String projectName);

  public void deleteProject(Long id);

  public boolean existsByProjectStatus(Long status);

  public boolean existsByProjectType(Long typeId);

  public List<ProjectResponse> findAllProjectByTypeId(Long id);
  
  public List<Project> getAllProjectByProjectTypeId(Long id);
  
  public boolean existsByProjectTypeAndProjectStatus(Long projectStatusId, Long projectTypeId);
  
  List<ProjectResponse> multiSearchProject(Pageable pageable, Pagination pagination,
      ProjectSearch projectSearch);
}
