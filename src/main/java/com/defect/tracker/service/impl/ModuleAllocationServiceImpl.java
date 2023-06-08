package com.defect.tracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.defect.tracker.repositories.ModuleAllocationRepository;
import com.defect.tracker.service.ModuleAllocationService;

@Service
public class ModuleAllocationServiceImpl implements ModuleAllocationService {
  @Autowired
  private ModuleAllocationRepository moduleAllocationRepository;

  @Override
  public boolean existsBySubModule(Long subModuleId) {
    return moduleAllocationRepository.existsBySubModuleId(subModuleId);
  }

}
