package com.defect.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.defect.tracker.entities.DefectStatus;
import com.defect.tracker.repositories.DefectStatusRepository;
import com.defect.tracker.response.dto.DefectStatusResponse;
import com.defect.tracker.resquest.dto.DefectStatusRequest;
import com.defect.tracker.service.DefectStatusService;

@Service
public class DefectStatusServiceImpl implements DefectStatusService {
  @Autowired
  private DefectStatusRepository defectstatusRepository;

  @Transactional
  public List<DefectStatusResponse> getAllDefectStatus() {
    List<DefectStatusResponse> defectStatusResponses = new ArrayList<>();
    List<DefectStatus> defectStatuses = defectstatusRepository.findAll();
    for (DefectStatus defectStatus : defectStatuses) {
      DefectStatusResponse defectStatusResponse = new DefectStatusResponse();
      BeanUtils.copyProperties(defectStatus, defectStatusResponse);
      defectStatusResponses.add(defectStatusResponse);
    }
    return defectStatusResponses;
  }

  @Override
  public boolean isDefectStatusExistsByColor(String color) {
    return defectstatusRepository.existsByColorIgnoreCase(color);
  }

  @Override
  public boolean existsByDefectStatus(Long id) {
    return defectstatusRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedDefectStatusNameExist(Long id, String name) {
    return defectstatusRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public boolean isUpdatedDefectStatusColorExist(Long id, String Color) {
    return defectstatusRepository.existsByColorIgnoreCaseAndIdNot(Color, id);
  }

  @Override
  public void deleteDefectStatus(Long id) {
    defectstatusRepository.deleteById(id);
  }

  @Override
  public boolean isDefectStatusExistByName(String name) {
    return defectstatusRepository.existsByNameIgnoreCase(name);
  }

  @Override
  public void saveDefectStatus(DefectStatusRequest defectstatusRequest) {
    DefectStatus defectStatus = new DefectStatus();
    BeanUtils.copyProperties(defectstatusRequest, defectStatus);
    defectstatusRepository.save(defectStatus);
  }

  @Transactional
  public DefectStatusResponse getDefectStatusById(Long id) {
    DefectStatus defectStatus = defectstatusRepository.findById(id).get();
    DefectStatusResponse defectStatusResponse = new DefectStatusResponse();
    BeanUtils.copyProperties(defectStatus, defectStatusResponse);
    return defectStatusResponse;
  }
}
