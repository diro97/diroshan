package com.defect.tracker.service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.response.dto.DefectTypeResponse;
import com.defect.tracker.resquest.dto.DefectTypeRequest;
import com.defect.tracker.search.response.DefectTypeSearch;

public interface DefectTypeService {
  public void saveDefectType(DefectTypeRequest defectTypeRequest);

  public List<DefectTypeResponse> getAllDefectType();

  public boolean isDefectTypeExists(String name);

  public DefectTypeResponse getDefectTypeById(Long id);

  public boolean existByDefectType(Long id);

  public boolean isUpdateDefectTypeNameExist(Long id, String name);

  public void deleteDefectType(Long id);

  List<DefectTypeResponse> multiSearchDefectType(Pageable pageable, Pagination pagination,
      DefectTypeSearch defectTypeSearch);
}
