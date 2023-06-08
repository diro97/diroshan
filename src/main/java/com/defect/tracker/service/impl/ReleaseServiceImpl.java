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
import com.defect.tracker.entities.QRelease;
import com.defect.tracker.entities.Release;
import com.defect.tracker.repositories.ReleaseRepository;
import com.defect.tracker.response.dto.ReleaseResponse;
import com.defect.tracker.resquest.dto.ReleaseRequest;
import com.defect.tracker.search.response.ReleaseSearch;
import com.defect.tracker.service.ReleaseService;
import com.defect.tracker.utils.Utils;
import com.querydsl.core.BooleanBuilder;

@Service
public class ReleaseServiceImpl implements ReleaseService {
  @Autowired
  private ReleaseRepository releaseRepository;

  @Transactional
  public void saveRelease(ReleaseRequest releaseRequest) {
    Release release = new Release();
    BeanUtils.copyProperties(releaseRequest, release);
    releaseRepository.save(release);
  }

  @Transactional
  public List<ReleaseResponse> getAllRelease() {
    List<ReleaseResponse> releaseResponses = new ArrayList<>();
    List<Release> releases = releaseRepository.findAll();
    for (Release release : releases) {
      ReleaseResponse releaseResponse = new ReleaseResponse();
      BeanUtils.copyProperties(release, releaseResponse);
      releaseResponses.add(releaseResponse);
    }
    return releaseResponses;
  }

  @Override
  public boolean isReleaseExists(String name) {
    return releaseRepository.existsByNameIgnoreCase(name);
  }

  @Transactional
  public ReleaseResponse getReleaseById(Long id) {
    Release release = releaseRepository.findById(id).get();
    ReleaseResponse releaseResponse = new ReleaseResponse();
    BeanUtils.copyProperties(release, releaseResponse);
    return releaseResponse;
  }

  @Override
  public boolean existByRelease(Long id) {
    return releaseRepository.existsById(id);
  }

  @Override
  public boolean isUpdatedReleaseNameExist(Long id, String name) {
    return releaseRepository.existsByNameIgnoreCaseAndIdNot(name, id);
  }

  @Override
  public void deleteRelease(Long id) {
    releaseRepository.deleteById(id);
  }

  @Transactional
  public List<ReleaseResponse> multiSearchRelease(Pageable pageable, Pagination pagination,
      ReleaseSearch releaseSearch) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    QRelease qRelease = QRelease.release;
    if (Utils.isNotNullAndEmpty(releaseSearch.getName())) {
      booleanBuilder.and(qRelease.name.containsIgnoreCase(releaseSearch.getName()));
    }
    List<ReleaseResponse> releaseResponses = new ArrayList<>();
    Page<Release> releases = releaseRepository.findAll(booleanBuilder, pageable);
    pagination.setTotalRecords(releases.getTotalElements());
    pagination.setTotalPages(releases.getTotalPages());
    for (Release release : releases.toList()) {
      ReleaseResponse releaseResponse = new ReleaseResponse();
      BeanUtils.copyProperties(release, releaseResponse);
      releaseResponses.add(releaseResponse);
    }
    return releaseResponses;
  }
}
