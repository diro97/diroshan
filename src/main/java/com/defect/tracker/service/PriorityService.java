package com.defect.tracker.service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.response.dto.PriorityResponse;
import com.defect.tracker.resquest.dto.PriorityRequest;
import com.defect.tracker.search.response.PrioritySearch;

public interface PriorityService {
  public void savePriority(PriorityRequest priorityRequest);

  public boolean isPriorityExists(String name);

  public List<PriorityResponse> getAllPriority();

  public PriorityResponse getPriorityById(Long id);

  public boolean existsByPriority(Long id);

  public boolean isUpdatedPriorityNameExist(Long id, String name);

  public void deletePriority(Long id);

  List<PriorityResponse> multiSearchPriority(Pageable pageable, Pagination pagination,
      PrioritySearch prioritySearch);
}
