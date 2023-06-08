package com.defect.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.entities.QSeverity;
import com.defect.tracker.entities.Severity;
import com.defect.tracker.repositories.SeverityRepository;
import com.defect.tracker.response.dto.SeverityResponse;
import com.defect.tracker.resquest.dto.SeverityRequest;
import com.defect.tracker.search.response.SeveritySearch;
import com.defect.tracker.service.SeverityService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;


@Service
public class SeverityServiceImpl implements SeverityService {
  @Autowired
  private SeverityRepository severityRepository;

  @Transactional
  public void saveSeverity(SeverityRequest severityRequest) {
    Severity severity = new Severity();
    BeanUtils.copyProperties(severityRequest, severity);
    severityRepository.save(severity);
  }

  @Transactional
  public List<SeverityResponse> getAllSeverities() {
    List<SeverityResponse> severityResponses = new ArrayList<>();
    List<Severity> severitys = severityRepository.findAll();
    for (Severity severity : severitys) {
      SeverityResponse severityResponse = new SeverityResponse();
      BeanUtils.copyProperties(severity, severityResponse);
      severityResponses.add(severityResponse);
    }
    return severityResponses;
  }

  @Override
  public boolean isSeverityExistsByName(String name) {
    return severityRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public SeverityResponse getSeverityById(Long id) {
    Severity severity = severityRepository.findById(id).get();
    SeverityResponse severityResponse = new SeverityResponse();
    BeanUtils.copyProperties(severity, severityResponse);
    return severityResponse;
  }

  @Override
  public boolean existBySeverity(Long id) {
    return severityRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedSeverityNameExist(Long id, String name) {
    return severityRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deleteSeverity(Long id) {
    severityRepository.deleteById(id);
  }

  @Override
  public boolean isSeverityExists(String color) {
    return severityRepository.existsByColorIgnoreCase(color);
  }

  @Override
  public boolean isUpdatedSeverityColorExist(Long id, String color) {
    return false;
  }

  @Transactional
  public List<SeverityResponse> multiSearchSeverity(Pageable pageable, Pagination pagination,
      SeveritySearch severitySearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QSeverity qSeverity = QSeverity.severity;
    if (Utils.isNotNullAndEmpty(severitySearch.getName())) {
      booleanBuilder.and(qSeverity.name.containsIgnoreCase(severitySearch.getName()));
    }
    List<SeverityResponse> severityResponses = new ArrayList<>();
    Page<Severity> severities = severityRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(severities.getTotalElements());
    pagination.setTotalPages(severities.getTotalPages());
    for (Severity severity : severities.toList()) {
      SeverityResponse severityResponse = new SeverityResponse();
      BeanUtils.copyProperties(severity, severityResponse);
      severityResponses.add(severityResponse);
    }
    return severityResponses;
  }
}
