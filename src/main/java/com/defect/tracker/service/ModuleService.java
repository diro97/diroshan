package com.defect.tracker.service;

import java.util.List;
import com.defect.tracker.response.dto.ModuleResponse;
import com.defect.tracker.resquest.dto.ModuleRequest;

public interface ModuleService {
  public void saveModule(ModuleRequest moduleRequest);

  public List<ModuleResponse> getALLModule();

  public boolean isModuleExists(String name);

  public ModuleResponse getModuleById(Long id);

  public boolean existByModule(Long id);

  public Boolean isUpdateModuleNameExist(Long id, String name);

  public void deleteModule(Long id);
}
