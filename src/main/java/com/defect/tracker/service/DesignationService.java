package com.defect.tracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.response.dto.DesignationResponse;
import com.defect.tracker.resquest.dto.DesignationRequest;
import com.defect.tracker.search.response.DesignationSearch;

public interface DesignationService {
  public void saveDesignation(DesignationRequest designationRequest);

  public List<DesignationResponse> getAllDesignation();

  public boolean isDesignationExists(String name);

  public DesignationResponse getDesignationById(Long id);

  public boolean existByDesignation(Long id);

  public boolean isUpdatedDesignationNameExist(Long id, String name);

  public void deleteDesignation(Long id);

  List<DesignationResponse> multiSearchDesignation(Pageable pageable, Pagination pagination,
      DesignationSearch designationSearch);
}
