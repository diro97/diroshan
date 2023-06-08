package com.defect.tracker.common.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatedContentResponse<T> extends ContentResponse<T> {
  private Map<String, T> result = new HashMap<>();

  public PaginatedContentResponse(String key, T dto, String status, String statusCode,
      String message) {
    super(status, dto, statusCode, message, message);
    result.put(key, dto);
  }

  private Pagination pagination;

  public PaginatedContentResponse(String key, T dto, String status, String statusCode,
      String message, Pagination pagination) {
    super(key, dto, status, statusCode, message);
    result.put(key, dto);
    this.pagination = pagination;
  }

  @Getter
  @Setter
  public static class Pagination {
    public Pagination(Integer pageNumber, Integer pageSize, Integer totalPages, Long totalRecords) {
      this.pageNumber = pageNumber;
      this.pageSize = pageSize;
      this.totalPages = totalPages;
      this.totalRecords = totalRecords;
    }

    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalRecords;
  }
}
