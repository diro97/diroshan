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
import com.defect.tracker.entities.Priority;
import com.defect.tracker.entities.QPriority;
import com.defect.tracker.repositories.PriorityRepository;
import com.defect.tracker.response.dto.PriorityResponse;
import com.defect.tracker.resquest.dto.PriorityRequest;
import com.defect.tracker.search.response.PrioritySearch;
import com.defect.tracker.service.PriorityService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class PriorityServiceImpl implements PriorityService {
  @Autowired
  private PriorityRepository priorityRepository;

  @Transactional
  public void savePriority(PriorityRequest priorityRequest) {
    Priority priority = new Priority();
    BeanUtils.copyProperties(priorityRequest, priority);
    priorityRepository.save(priority);
  }

  @Override
  public boolean isPriorityExists(String name) {
    return priorityRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public List<PriorityResponse> getAllPriority() {
    List<PriorityResponse> priorityResponses = new ArrayList<>();
    List<Priority> priorities = priorityRepository.findAll();
    for (Priority priority : priorities) {
      PriorityResponse priorityResponse = new PriorityResponse();
      BeanUtils.copyProperties(priority, priorityResponse);
      priorityResponses.add(priorityResponse);
    }
    return priorityResponses;
  }

  @Transactional
  public PriorityResponse getPriorityById(Long id) {
    Priority priority = priorityRepository.findById(id).get();
    PriorityResponse priorityResponse = new PriorityResponse();
    BeanUtils.copyProperties(priority, priorityResponse);
    return priorityResponse;
  }

  @Override
  public boolean existsByPriority(Long id) {
    return priorityRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedPriorityNameExist(Long id, String name) {
    return priorityRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deletePriority(Long id) {
    priorityRepository.deleteById(id);
  }

  @Transactional
  public List<PriorityResponse> multiSearchPriority(Pageable pageable, Pagination pagination,
      PrioritySearch prioritySearch) {

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QPriority qPriority = QPriority.priority;
    if (Utils.isNotNullAndEmpty(prioritySearch.getName())) {
      booleanBuilder.and(qPriority.name.containsIgnoreCase(prioritySearch.getName()));
    }
    List<PriorityResponse> priorityResponses = new ArrayList<>();
    Page<Priority> priorities = priorityRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(priorities.getTotalElements());
    pagination.setTotalPages(priorities.getTotalPages());
    for (Priority priority : priorities.toList()) {
      PriorityResponse priorityResponse = new PriorityResponse();
      BeanUtils.copyProperties(priority, priorityResponse);
      priorityResponses.add(priorityResponse);
    }
    return priorityResponses;
  }

}

