package com.defect.tracker.service.impl;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.DefectType;
import com.defect.tracker.entities.QDefectType;
import com.defect.tracker.repositories.DefectTypeRepository;
import com.defect.tracker.response.dto.DefectTypeResponse;
import com.defect.tracker.resquest.dto.DefectTypeRequest;
import com.defect.tracker.search.response.DefectTypeSearch;
import com.defect.tracker.service.DefectTypeService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class DefectTypeServiceImpl implements DefectTypeService {
  @Autowired
  private DefectTypeRepository defectTypeRepository;

  @Transactional
  public void saveDefectType(DefectTypeRequest defectTypeRequest) {
    DefectType defectType = new DefectType();
    BeanUtils.copyProperties(defectTypeRequest, defectType);
    defectTypeRepository.save(defectType);
  }

  @Transactional
  public List<DefectTypeResponse> getAllDefectType() {
    List<DefectTypeResponse> defectTypeResponses = new ArrayList<>();
    List<DefectType> defectTypes = defectTypeRepository.findAll();
    for (DefectType defectType : defectTypes) {
      DefectTypeResponse defectTypeResponse = new DefectTypeResponse();
      BeanUtils.copyProperties(defectType, defectTypeResponse);
      defectTypeResponses.add(defectTypeResponse);
    }
    return defectTypeResponses;
  }

  @Override
  public boolean isDefectTypeExists(String name) {
    return defectTypeRepository.existsByNameIgnoreCase(name);
  }

  @Override
  public boolean existByDefectType(Long id) {
    return defectTypeRepository.existsById(id);
  }

  @Override
  public boolean isUpdateDefectTypeNameExist(Long id, String name) {
    return defectTypeRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deleteDefectType(Long id) {
    defectTypeRepository.deleteById(id);
  }

  @Transactional
  public DefectTypeResponse getDefectTypeById(Long id) {
    DefectType defectType = defectTypeRepository.findById(id).get();
    DefectTypeResponse defectTypeResponse = new DefectTypeResponse();
    BeanUtils.copyProperties(defectType, defectTypeResponse);
    return defectTypeResponse;
  }

  @Transactional
  public List<DefectTypeResponse> multiSearchDefectType(Pageable pageable, Pagination pagination,
      DefectTypeSearch defectTypeSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QDefectType qDefectType = QDefectType.defectType;
    if (Utils.isNotNullAndEmpty(defectTypeSearch.getName())) {
      booleanBuilder.and(qDefectType.name.containsIgnoreCase(defectTypeSearch.getName()));
    }
    List<DefectTypeResponse> defectTypeResponses = new ArrayList<>();
    Page<DefectType> defectTypes = defectTypeRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(defectTypes.getTotalElements());
    pagination.setTotalPages(defectTypes.getTotalPages());
    for (DefectType defectType : defectTypes.toList()) {
      DefectTypeResponse defectTypeResponse = new DefectTypeResponse();
      BeanUtils.copyProperties(defectType, defectTypeResponse);
      defectTypeResponses.add(defectTypeResponse);
    }
    return defectTypeResponses;
  }
}
