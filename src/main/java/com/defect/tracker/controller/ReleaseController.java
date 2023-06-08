package com.defect.tracker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.defect.tracker.common.response.BaseResponse;
import com.defect.tracker.common.response.ContentResponse;
import com.defect.tracker.common.response.PaginatedContentResponse;
import com.defect.tracker.common.response.PaginatedContentResponse.Pagination;
import com.defect.tracker.resquest.dto.ReleaseRequest;
import com.defect.tracker.rest.enums.RequestStatus;
import com.defect.tracker.search.response.ReleaseSearch;
import com.defect.tracker.service.DefectService;
import com.defect.tracker.service.ReleaseService;
import com.defect.tracker.utils.Constants;
import com.defect.tracker.utils.EndpointURI;
import com.defect.tracker.utils.ValidationFailureResponseCode;

@RestController
@CrossOrigin
public class ReleaseController {
  @Autowired
  private ReleaseService releaseService;
  @Autowired
  private ValidationFailureResponseCode validationFailureResponseCode;
  @Autowired
  private DefectService defectService;
  private static final Logger logger = LoggerFactory.getLogger(ReleaseController.class);

  @PostMapping(value = EndpointURI.RELEASE)
  public ResponseEntity<Object> saveRelease(@RequestBody ReleaseRequest releaseRequest) {
    if (releaseService.isReleaseExists(releaseRequest.getName())) {
      logger.info("Release already exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getReleaseAlreadyExists(),
          validationFailureResponseCode.getValidationReleaseAlreadyExists()));
    }
    logger.info("Release added successfully");
    releaseService.saveRelease(releaseRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSaveReleaseSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.RELEASE)
  public ResponseEntity<Object> getAllRelease() {
    logger.info("All release retrieved successfully");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.RELEASES, releaseService.getAllRelease(),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetAllReleaseSuccessMessage()));
  }

  @GetMapping(value = EndpointURI.RELEASE_BY_ID)
  public ResponseEntity<Object> getReleaseById(@PathVariable Long id) {
    if (!releaseService.existByRelease(id)) {
      logger.info("Relese is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getReleaseNotExistsCode(),
          validationFailureResponseCode.getReleaseNotExistsMessage()));
    }
    logger.info("Release retrieved successfully for given id");
    return ResponseEntity
        .ok(new ContentResponse<>(Constants.RELEASE, releaseService.getReleaseById(id),
            RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
            validationFailureResponseCode.getGetReleaseByIdSuccessMessage()));
  }

  @DeleteMapping(value = EndpointURI.RELEASE_BY_ID)
  public ResponseEntity<Object> deleteRelease(@PathVariable Long id) {
    if (!releaseService.existByRelease(id)) {
      logger.info("Relese is not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getReleaseNotExistsCode(),
          validationFailureResponseCode.getReleaseNotExistsMessage()));
    }
    if (defectService.existsByRelease(id)) {
      logger.info("It's mapped on defect can't delete");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getReleaseCanNotDeleteCode(),
          validationFailureResponseCode.getReleasecanNotDeleteMessage()));
    }
    logger.info("Relese deleted successfully");
    releaseService.deleteRelease(id);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getDeleteReleaseSuccessMessage()));
  }

  @PutMapping(value = EndpointURI.RELEASE)
  public ResponseEntity<Object> updateRelease(@RequestBody ReleaseRequest releaseRequest) {
    if (!releaseService.existByRelease(releaseRequest.getId())) {
      logger.info("Release not exists");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getReleaseNotExistsCode(),
          validationFailureResponseCode.getReleaseNotExistsMessage()));
    }
    if (releaseService.isUpdatedReleaseNameExist(releaseRequest.getId(),
        releaseRequest.getName())) {
      logger.info("Release already exits");
      return ResponseEntity.ok(new BaseResponse(RequestStatus.FAILURE.getStatus(),
          validationFailureResponseCode.getReleaseAlreadyExists(),
          validationFailureResponseCode.getValidationReleaseAlreadyExists()));
    }
    logger.info("Release updated successfully");
    releaseService.saveRelease(releaseRequest);
    return ResponseEntity.ok(new BaseResponse(RequestStatus.SUCCESS.getStatus(),
        validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getUpdateReleaseSuccessMessage()));
  }

  @GetMapping(EndpointURI.RELEASE_SEARCH)
  public ResponseEntity<Object> multiSearchRelease(@RequestParam(name = "page") int page,
      @RequestParam(name = "size") int size, @RequestParam(name = "sortField") String sortField,
      @RequestParam(name = "direction") String direction, ReleaseSearch releaseSearch) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.valueOf(direction), sortField);
    Pagination pagination = new Pagination(page, size, 0, 0L);
    return ResponseEntity.ok(new PaginatedContentResponse<>(Constants.RELEASE,
        releaseService.multiSearchRelease(pageable, pagination, releaseSearch),
        RequestStatus.SUCCESS.getStatus(), validationFailureResponseCode.getCommonSuccessCode(),
        validationFailureResponseCode.getSearchReleaseSuccessMessage(), pagination));
  }
}
