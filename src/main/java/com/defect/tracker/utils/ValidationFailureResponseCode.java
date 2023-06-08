package com.defect.tracker.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@PropertySource("classpath:MessagesAndCodes.properties")
@Getter
@Setter
public class ValidationFailureResponseCode {
  // Common Success code
  @Value("${code.success.common}")
  private String commonSuccessCode;

  @Value("${code.failure.common}")
  private String failureCode;

  // Validation code for Designation
  @Value("${code.validation.designation.alreadyExists}")
  private String designationAlreadyExists;

  @Value("${code.validation.designation.notExists}")
  private String designationNotExistsCode;

  // Messages for Designation
  @Value("${message.success.save.designation}")
  private String saveDesignationSuccessMessage;

  @Value("${message.validation.designation.alreadyExists}") 
  private String validationDesignationAlreadyExists;

  @Value("${message.success.getAll.designation}")
  private String getAllDesignationSuccessMessage;

  @Value("${message.validation.designation.notExists}")
  private String designationNotExistsMessage;

  @Value("${message.success.getById.designation}")
  private String getDesignationByIdSuccessMessage;

  @Value("${message.success.update.designation}")
  private String updateDesignationSuccessMessage;

  @Value("${message.success.delete.designation}")
  private String deleteDesignationSuccessMessage;

  // Validation code for projectStatus
  @Value("${code.validation.projectStatus.alreadyExists}")
  private String projectStatusAlreadyExists;

  @Value("${code.validation.projectStatus.notExists}")
  private String projectStatusNotExistsCode;

  @Value("${code.validation.projectStatus.canNotDelete}")
  private String projectStatusCanNotDeleteCode;

  // Messages for ProjectStatus
  @Value("${message.success.save.projectStatus}")
  private String saveProjectStatusSuccessMessage;

  @Value("${message.validation.projectStatus.alreadyExists}")
  private String validationProjectStatusAlreadyExists;

  @Value("${message.success.getAll.projectStatus}")
  private String getAllProjectStatusSuccessMessage;

  @Value("${message.validation.projectStatus.notExists}")
  private String projectStatusNotExistsMessage;

  @Value("${message.success.getById.projectStatus}")
  private String getProjectStatusByIdSuccessMessage;

  @Value("${message.success.update.projectStatus}")
  private String updateProjectStatusSuccessMessage;

  @Value("${message.success.delete.projectStatus}")
  private String deleteProjectStatusSuccessMessage;

  @Value("${message.validation.projectStatus.canNotDelete}")
  private String projectStatusCanNotDeleteMessage;

  @Value("${message.success.search.projectStatus}")
  private String searchProjectStatusSuccessMessage;

  @Value("${message.success.search.designation}")
  private String searchDesignationSuccessMessage;

  // Validation Code for DefectType
  @Value("${code.validation.defectType.alreadyExists}")
  private String defectTypeAlreadyExists;

  @Value("${code.validation.defectType.notExists}")
  private String defectTypeNotExistsCode;

  @Value("${code.validation.defectType.canNotDelete")
  private String DefectTypeCanNotDeleteCode;

  // Messages for DefectType
  @Value("${message.success.save.defectType}")
  private String saveDefectTypeSuccessMessage;

  @Value("${message.validation.defectType.alreadyExists}")
  private String validationDefectTypeAlreadyExists;

  @Value("${message.success.getAll.defectType}")
  private String getAllDefectTypeSuccessMessage;

  @Value("${message.validation.defectType.notExists}")
  private String defectTypeNotExistsMessage;

  @Value("${message.success.getById.defectType}")
  private String getDefectTypeByIdSuccessMessage;

  @Value("${message.success.update.defectType}")
  private String updateDefectTypeSuccessMessage;

  @Value("${message.success.delete.defectType}")
  private String deleteDefectTypeSuccessMessage;

  @Value("${message.validation.defectType.canNotDelete}")
  private String defectTypecanNotDeleteMessage;

  @Value("${message.success.search.defectType}")
  private String searchDefectTypeSuccessMessage;

  // Validation code for Defect-Status
  @Value("${code.validation.defectstatus.alreadyExists}")
  private String defectstatusAlreadyExists;

  @Value("${code.validation.defectstatus.notExists}")
  private String defectstatusNotExistscode;

  @Value("${code.validation.defectstatus.canNotDelete}")
  private String defectstatuscanNotDeleteCode;

  // Messages for DefectStatus
  @Value("${message.success.save.defectstatus}")
  private String saveDefectStatusSuccessMessage;

  @Value("${message.validation.defectstatus.alreadyExists}")
  private String validationDefectStatusAlreadyExists;

  @Value("${message.success.getAll.defectstatus}")
  private String getAllDefectStatusSuccessMessage;

  @Value("${message.validation.defectstatus.notExists}")
  private String defectstatusNotExistsMessage;

  @Value("${message.success.getById.defectstatus}")
  private String getDefectStatusByIdSuccessMessage;

  @Value("${message.success.update.defectstatus}")
  private String updateDefectStatusSuccessMessage;

  @Value("${message.success.delete.defectstatus}")
  private String deleteDefectStatusSuccessMessage;

  @Value("${message.validation.defectstatus.canNotDelete}")
  private String DefectStatusCanNotDeleteMessage;

  // Validation code for module
  @Value("${code.validation.module.alreadyExists}")
  private String moduleAlreadyExists;

  @Value("${code.validation.module.notExists}")
  private String moduleNotExistsCode;

  // Messages for module
  @Value("${message.success.save.module}")
  private String saveModuleSuccessMessage;

  @Value("${message.validation.module.alreadyExists}")
  private String validationModuleAlreadyExists;

  @Value("${message.success.getAll.module}")
  private String getAllModuleSuccessMessage;

  @Value("${message.validation.module.notExists}")
  private String moduleNotExistsMessage;

  @Value("${message.success.getById.module}")
  private String getModuleByIdSuccessMessage;

  @Value("${message.success.update.module}")
  private String updateModuleSuccessMessage;

  @Value("${message.success.delete.module}")
  private String deleteModuleSuccessMessage;

  // Validation code for Severity
  @Value("${code.validation.severity.alreadyExists}")
  private String severityAlreadyExists;

  @Value("${code.validation.severity.notExists}")
  private String severityNotExistsCode;

  @Value("${code.validation.severity.canNotDelete}")
  private String SeverityCanNotDeleteCode;

  // Messages for Severity
  @Value("${message.success.save.severity}")
  private String saveSeveritySuccessMessage;

  @Value("${message.validation.severity.alreadyExists}")
  private String validationSeverityAlreadyExists;

  @Value("${message.success.getAll.severities}")
  private String getAllSeveritiesSuccessMessage;

  @Value("${message.validation.severity.notExists}")
  private String severityNotExistsMessage;

  @Value("${message.success.getById.severity}")
  private String getSeverityByIdSuccessMessage;

  @Value("${message.success.update.severity}")
  private String updateSeveritySuccessMessage;

  @Value("${message.success.delete.severity}")
  private String deleteSeveritySuccessMessage;

  @Value("${message.validation.severity.canNotDelete}")
  private String SeverityCanNotDeleteMessage;

  @Value("${message.success.search.severity}")
  private String searchSeveritySuccessMessage;

  // Validation code for Priority
  @Value("${code.validation.priority.alreadyExists}")
  private String priorityAlreadyExists;

  @Value("${code.validation.priority.notExists}")
  private String priorityNotExistsCode;

  @Value("${code.validation.priority.canNotDelete}")
  private String priorityCanNotDeleteCode;

  // Messages for Priority
  @Value("${message.success.save.priority}")
  private String savePrioritySuccessMessage;

  @Value("${message.validation.priority.alreadyExists}")
  private String validationPriorityAlreadyExists;

  @Value("${message.success.getAll.priority}")
  private String getAllPrioritySuccessMessage;

  @Value("${message.success.getById.priority}")
  private String getPriorityByIdSuccessMessage;

  @Value("${message.validation.priority.notExists}")
  private String priorityNotExistsMessage;

  @Value("${message.success.delete.priority}")
  private String deletePrioritySuccessMessage;

  @Value("${message.success.update.priority}")
  private String updatePrioritySuccessMessage;

  @Value("${message.validation.priority.canNotDelete}")
  private String priorityCanNotDeleteMessage;

  @Value("${message.success.search.priority}")
  private String searchPrioritySuccessMessage;

  // Validation code for Employee
  @Value("${code.validation.employee.alreadyExists}")
  private String employeAlreadyExists;

  @Value("${code.validation.employee.notExists}")
  private String employeeNotExistsCode;

  @Value("${code.validation.employee.canNotDelete}")
  private String employeeCanNotDeleteCode;

  // Messages for Employee
  @Value("${message.success.save.employee}")
  private String saveEmployeeSuccessMessage;

  @Value("${message.validation.employee.alreadyExists}")
  private String validationEmployeeAlreadyExists;

  @Value("${message.success.getAll.employee}")
  private String getAllEmployeeSuccessMessage;

  @Value("${message.validation.employee.notExists}")
  private String employeeNotExistsMessage;

  @Value("${message.success.getById.employee}")
  private String getEmployeeByIdSuccessMessage;

  @Value("${message.success.update.employee}")
  private String updateEmployeeSuccessMessage;

  @Value("${message.success.delete.employee}")
  private String deleteEmployeeSuccessMessage;

  @Value("${message.validation.employee.canNotDelete}")
  private String employeeCanNotDeleteMessage;

  @Value("${message.success.search.employee}")
  private String searchEmployeeSuccessMessage;


  // Validation code for Release
  @Value("${code.validation.release.alreadyExists}")
  private String releaseAlreadyExists;

  @Value("${code.validation.release.notExists}")
  private String releaseNotExistsCode;

  @Value("${code.validation.release.canNotDelete")
  private String releaseCanNotDeleteCode;

  // Messages for Release
  @Value("${message.success.save.release}")
  private String saveReleaseSuccessMessage;

  @Value("${message.validation.release.alreadyExists}")
  private String validationReleaseAlreadyExists;

  @Value("${message.success.getAll.release}")
  private String getAllReleaseSuccessMessage;

  @Value("${message.validation.release.notExists}")
  private String releaseNotExistsMessage;

  @Value("${message.success.getById.release}")
  private String getReleaseByIdSuccessMessage;

  @Value("${message.success.update.release}")
  private String updateReleaseSuccessMessage;

  @Value("${message.success.delete.release}")
  private String deleteReleaseSuccessMessage;

  @Value("${message.validation.release.canNotDelete}")
  private String releasecanNotDeleteMessage;

  @Value("${message.success.search.release}")
  private String searchReleaseSuccessMessage;

  // Validation code for Role
  @Value("${code.validation.role.alreadyExists}")
  private String roleAlreadyExists;

  @Value("${code.validation.role.notExists}")
  private String roleNotExistesCode;

  @Value("${code.validation.role.canNotDelete}")
  private String roleCanNotDeleteCode;

  // message for Role
  @Value("${message.success.save.role}")
  private String saveRoleSuccessMessage;

  @Value("${message.validation.role.alreadyExists}")
  private String validationRoleAlreadyExists;

  @Value("${message.success.getAll.role}")
  private String getAllRoleSuccessMessage;

  @Value("${message.validation.role.notExists}")
  private String roleNotExistsMessage;

  @Value("${message.success.getById.role}")
  private String getRoleByIdSuccessMessage;

  @Value("${message.success.update.role}")
  private String updateRoleSuccessMessage;

  @Value("${message.success.delete.role}")
  private String deleteRoleSuccessMessage;

  @Value("${message.validation.role.canNotDelete}")
  private String rolecanNotDeleteMessage;

  @Value("${message.success.search.role}")
  private String searchRoleSuccessMessage;

  // Validation code for ProjectType
  @Value("${code.validation.projectType.alreadyExists}")
  private String projectTypeAlreadyExists;

  @Value("${code.validation.projectType.notExists}")
  private String projectTypeNotExistesCode;

  @Value("${code.validation.projectType.canNotDelete}")
  private String projectTypeCanNotDeletecode;

  // message for ProjectType
  @Value("${message.success.save.projectType}")
  private String saveProjectTypeSuccessMessage;

  @Value("${message.validation.projectType.alreadyExists}")
  private String validationProjectTypeAlreadyExists;

  @Value("${message.success.getAll.projectType}")
  private String getAllProjectTypeSuccessMessage;

  @Value("${message.validation.projectType.notExists}")
  private String projectTypeNotExistsMessage;

  @Value("${message.success.getById.projectType}")
  private String getProjectTypeByIdSuccessMessage;

  @Value("${message.success.update.projectType}")
  private String updateProjectTypeSuccessMessage;

  @Value("${message.success.delete.projectType}")
  private String deleteProjectTypeSuccessMessage;

  @Value("${message.validation.projectType.canNotDelete}")
  private String projectTypecanNotDeleteMessage;

  @Value("${message.success.search.projectType}")
  private String searchProjectTypeSuccessMessage;

  // Validation code for Project
  @Value("${code.validation.project.alreadyExists}")
  private String projectAlreadyExists;

  @Value("${code.validation.project.notExists}")
  private String projectNotExistesCode;

  @Value("${code.validation.project.canNotDelete}")
  private String projectCanNotDeletecode;

  // message for Project
  @Value("${message.success.save.project}")
  private String saveProjectSuccessMessage;

  @Value("${message.validation.project.alreadyExists}")
  private String validationProjectAlreadyExists;

  @Value("${message.success.getAll.projectType}")
  private String getAllProjectSuccessMessage;

  @Value("${message.validation.project.notExists}")
  private String projectNotExistsMessage;

  @Value("${message.success.getById.project}")
  private String getProjectByIdSuccessMessage;

  @Value("${message.success.update.project}")
  private String updateProjectSuccessMessage;

  @Value("${message.success.delete.project}")
  private String deleteProjectSuccessMessage;

  @Value("${message.validation.project.canNotDelete}")
  private String projectCanNotDeleteMessage;

  @Value("${message.success.search.project}")
  private String searchProjectSuccessMessage;

  // Validation code for SubModule
  @Value("${code.validation.submodule.alreadyExists}")
  private String subModuleAlreadyExists;

  @Value("${code.validation.submodule.notExists}")
  private String subModuleNotExistsCode;

  @Value("${code.validation.subModule.canNotDelete}")
  private String subModuleCanNotDeletecode;

  // Messages for submodule
  @Value("${message.success.save.submodule}")
  private String saveSubmoduleSuccessMessage;

  @Value("${message.validation.submodule.alreadyExists}")
  private String validationSubmoduleAlreadyExists;

  @Value("${message.success.getAll.submodule}")
  private String getAllSubmoduleSuccessMessage;

  @Value("${message.validation.submodule.notExists}")
  private String submoduleNotExistsMessage;

  @Value("${message.success.getById.submodule}")
  private String getSubmoduleByIdSuccessMessage;

  @Value("${message.success.update.submodule}")
  private String updateSubmoduleSuccessMessage;

  @Value("${message.success.delete.submodule}")
  private String deleteSubmoduleSuccessMessage;

  @Value("${message.validation.subModule.canNotDelete}")
  private String subModulecanNotDeleteMessage;

  @Value("${message.success.search.subModule}")
  private String searchSubModuleSuccessMessage;

}
