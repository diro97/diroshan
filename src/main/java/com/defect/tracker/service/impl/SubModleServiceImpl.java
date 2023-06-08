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
import com.defect.tracker.entities.Module;
import com.defect.tracker.entities.QModule;
import com.defect.tracker.entities.QSubModule;
import com.defect.tracker.entities.SubModule;
import com.defect.tracker.repositories.SubModuleRepository;
import com.defect.tracker.response.dto.SubModuleResponse;
import com.defect.tracker.resquest.dto.SubModuleRequest;
import com.defect.tracker.search.response.SubModuleSearch;
import com.defect.tracker.service.SubModuleService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class SubModleServiceImpl implements SubModuleService {
  @Autowired
  private SubModuleRepository subModuleRepository;

  @Transactional
  public void saveSubModule(SubModuleRequest subModuleRequest) {
    SubModule subModule = new SubModule();
    BeanUtils.copyProperties(subModuleRequest, subModule);
    Module module = new Module();
    module.setId(subModuleRequest.getModuleId());
    subModule.setModule(module);
    subModuleRepository.save(subModule);
  }

  @Transactional
  public List<SubModuleResponse> getAllSubModule() {
    List<SubModuleResponse> subModuleResponses = new ArrayList<>();
    List<SubModule> subModules = subModuleRepository.findAll();
    for (SubModule subModule : subModules) {
      SubModuleResponse subModuleResponse = new SubModuleResponse();
      BeanUtils.copyProperties(subModule, subModuleResponse);
      subModuleResponse.setModuleId(subModule.getModule().getId());
      subModuleResponse.setModuleName(subModule.getModule().getName());
      subModuleResponses.add(subModuleResponse);
    }
    return subModuleResponses;
  }

  @Override
  public boolean isSubModuleExists(String name) {
    return subModuleRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public SubModuleResponse getSubModuleById(Long id) {
    SubModule subModule = subModuleRepository.findById(id).get();
    SubModuleResponse subModuleResponse = new SubModuleResponse();
    BeanUtils.copyProperties(subModule, subModuleResponse);
    subModuleResponse.setModuleId(subModule.getModule().getId());
    subModuleResponse.setModuleName(subModule.getModule().getName());
    return subModuleResponse;
  }

  @Override
  public boolean existsBySubModule(Long id) {
    return subModuleRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedSubModuleNameExist(Long id, String name) {
    return subModuleRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deleteSubModule(Long id) {
    subModuleRepository.deleteById(id);
  }

  @Override
  public List<SubModule> getAllSubModuleByModuleId(Long id) {
    return subModuleRepository.findByModuleId(id);
  }

  @Override
  public List<SubModuleResponse> multiSearchSubModule(Pageable pageable, Pagination pagination,
      SubModuleSearch subModuleSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QSubModule qSubModule = QSubModule.subModule;
    if (Utils.isNotNullAndEmpty(subModuleSearch.getName())) {
      booleanBuilder.and(qSubModule.name.containsIgnoreCase(subModuleSearch.getName()));
    }
    if (Utils.isNotNullAndEmpty(subModuleSearch.getModule())) {
      QModule qModule = QModule.module;
      booleanBuilder.and(qModule.name.containsIgnoreCase(subModuleSearch.getModule()));
    }
    List<SubModuleResponse> subModuleResponses = new ArrayList<>();
    Page<SubModule> subModules = subModuleRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(subModules.getTotalElements());
    pagination.setTotalPages(subModules.getTotalPages());
    for (SubModule subModule : subModules.toList()) {
      SubModuleResponse subModuleResponse = new SubModuleResponse();
      BeanUtils.copyProperties(subModule, subModuleResponse);
      subModuleResponses.add(subModuleResponse);
    }
    return subModuleResponses;
  }

  @Override
  public boolean existsByModuleId(Long id) {
    return subModuleRepository.existsByModuleId(id);
  }
}
