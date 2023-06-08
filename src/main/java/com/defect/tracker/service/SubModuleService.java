package com.defect.tracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.SubModule;
import com.defect.tracker.response.dto.SubModuleResponse;
import com.defect.tracker.resquest.dto.SubModuleRequest;
import com.defect.tracker.search.response.SubModuleSearch;

public interface SubModuleService {
  public void saveSubModule(SubModuleRequest subModuleRequest);

  public List<SubModuleResponse> getAllSubModule();

  public boolean isSubModuleExists(String name);

  public SubModuleResponse getSubModuleById(Long id);

  public boolean existsBySubModule(Long id);

  public boolean isUpdatedSubModuleNameExist(Long id, String name);

  public void deleteSubModule(Long id);

  public List<SubModule> getAllSubModuleByModuleId(Long id);

  public List<SubModuleResponse> multiSearchSubModule(Pageable pageable, Pagination pagination,
      SubModuleSearch subModuleSearch);
  
 public boolean existsByModuleId(Long id);
}
