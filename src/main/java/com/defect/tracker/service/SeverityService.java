package com.defect.tracker.service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.response.dto.SeverityResponse;
import com.defect.tracker.resquest.dto.SeverityRequest;
import com.defect.tracker.search.response.SeveritySearch;


public interface SeverityService {
  public void saveSeverity(SeverityRequest severityRequest);

  public List<SeverityResponse> getAllSeverities();

  public boolean isSeverityExistsByName(String name);

  public boolean isSeverityExists(String color);

  public SeverityResponse getSeverityById(Long id);

  public boolean existBySeverity(Long id);

  public boolean isUpdatedSeverityNameExist(Long id, String name);

  public boolean isUpdatedSeverityColorExist(Long id, String color);

  public void deleteSeverity(Long id);

  List<SeverityResponse> multiSearchSeverity(Pageable pageable, Pagination pagination,
      SeveritySearch severitySearch);
}
