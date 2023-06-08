package com.defect.tracker.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.defect.tracker.entities.Module;
import com.defect.tracker.repositories.ModuleRepository;
import com.defect.tracker.response.dto.ModuleResponse;
import com.defect.tracker.resquest.dto.ModuleRequest;
import com.defect.tracker.service.ModuleService;

@Service
public class ModuleServiceImpl implements ModuleService {
  @Autowired
  ModuleRepository moduleRepository;

  @Transactional
  public void saveModule(ModuleRequest moduleRequest) {
    Module module = new Module();
    BeanUtils.copyProperties(moduleRequest, module);
    moduleRepository.save(module);
  }

  @Transactional
  public List<ModuleResponse> getALLModule() {
    List<ModuleResponse> moduleResponses = new ArrayList<>();
    List<Module> modules = moduleRepository.findAll();
    for (Module module : modules) {
      ModuleResponse moduleResponse = new ModuleResponse();
      BeanUtils.copyProperties(module, moduleResponse);
      moduleResponses.add(moduleResponse);
    }
    return moduleResponses;
  }

  @Override
  public boolean isModuleExists(String name) {
    return moduleRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public ModuleResponse getModuleById(Long id) {
    Module module = moduleRepository.findById(id).get();
    ModuleResponse moduleResponse = new ModuleResponse();
    BeanUtils.copyProperties(module, moduleResponse);
    return moduleResponse;
  }

  @Override
  public boolean existByModule(Long id) {
    return moduleRepository.existsById(id);
  }

  @Override
  public Boolean isUpdateModuleNameExist(Long id, String name) {
    return moduleRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deleteModule(Long id) {
    moduleRepository.deleteById(id);
  }
}
