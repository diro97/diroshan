package com.defect.tracker.service;

import java.util.List;
import com.defect.tracker.response.dto.DefectStatusResponse;
import com.defect.tracker.resquest.dto.DefectStatusRequest;

public interface DefectStatusService {
  public void saveDefectStatus(DefectStatusRequest defectstatusRequest);

  public List<DefectStatusResponse> getAllDefectStatus();

  public boolean isDefectStatusExistByName(String name);

  public boolean isDefectStatusExistsByColor(String color);

  public DefectStatusResponse getDefectStatusById(Long id);

  public boolean existsByDefectStatus(Long id);

  public boolean isUpdatedDefectStatusNameExist(Long id, String name);

  public boolean isUpdatedDefectStatusColorExist(Long id, String Color);

  public void deleteDefectStatus(Long id);
}
