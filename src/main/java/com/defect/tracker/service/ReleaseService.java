package com.defect.tracker.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.response.dto.ReleaseResponse;
import com.defect.tracker.resquest.dto.ReleaseRequest;
import com.defect.tracker.search.response.ReleaseSearch;

public interface ReleaseService {
  public void saveRelease(ReleaseRequest releaseRequest);

  public List<ReleaseResponse> getAllRelease();

  public boolean isReleaseExists(String name);

  public ReleaseResponse getReleaseById(Long id);

  public boolean existByRelease(Long id);

  public boolean isUpdatedReleaseNameExist(Long id, String name);

  public void deleteRelease(Long id);

  public List<ReleaseResponse> multiSearchRelease(Pageable pageable, Pagination pagination,
      ReleaseSearch releaseSearch);
}
