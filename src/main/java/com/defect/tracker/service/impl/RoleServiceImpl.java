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
import com.defect.tracker.entities.QRole;
import com.defect.tracker.entities.Role;
import com.defect.tracker.repositories.RoleRepository;
import com.defect.tracker.response.dto.RoleResponse;
import com.defect.tracker.resquest.dto.RoleRequest;
import com.defect.tracker.search.response.RoleSearch;
import com.defect.tracker.service.RoleService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Transactional
  public void saveRole(RoleRequest roleRequest) {
    Role role = new Role();
    BeanUtils.copyProperties(roleRequest, role);
    roleRepository.save(role);
  }

  @Transactional
  public List<RoleResponse> getAllRole() {
    List<RoleResponse> roleResponses = new ArrayList<>();
    List<Role> roles = roleRepository.findAll();
    for (Role role : roles) {
      RoleResponse roleResponse = new RoleResponse();
      BeanUtils.copyProperties(role, roleResponse);
      roleResponses.add(roleResponse);
    }
    return roleResponses;
  }

  @Override
  public boolean isRoleExists(String name) {
    return roleRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public RoleResponse getRoleById(Long id) {
    Role role = roleRepository.findById(id).get();
    RoleResponse roleResponse = new RoleResponse();
    BeanUtils.copyProperties(role, roleResponse);
    return roleResponse;
  }

  @Override
  public boolean existByRole(Long id) {
    return roleRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedRoleNameExist(Long id, String name) {
    return roleRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deleteRole(Long id) {
    roleRepository.deleteById(id);
  }

  @Transactional
  public List<RoleResponse> multiSearchRole(Pageable pageable, Pagination pagination,
      RoleSearch roleSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QRole qRole = QRole.role;
    if (Utils.isNotNullAndEmpty(roleSearch.getName())) {
      booleanBuilder.and(qRole.name.containsIgnoreCase(roleSearch.getName()));
    }
    List<RoleResponse> roleResponses = new ArrayList<>();
    Page<Role> roles = roleRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(roles.getTotalElements());
    pagination.setTotalPages(roles.getTotalPages());
    for (Role role : roles.toList()) {
      RoleResponse roleResponse = new RoleResponse();
      BeanUtils.copyProperties(role, roleResponse);
      roleResponses.add(roleResponse);
    }
    return roleResponses;
  }
}
