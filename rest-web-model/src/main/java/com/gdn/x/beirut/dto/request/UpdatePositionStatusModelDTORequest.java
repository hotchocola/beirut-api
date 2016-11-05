package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

public class UpdatePositionStatusModelDTORequest extends BaseRequest {

  private static final long serialVersionUID = -4152825937934296238L;

  private String statusDTORequest;

  private List<String> idPositions;

  public List<String> getIdPositions() {
    return idPositions;
  }

  public String getStatusDTORequest() {
    return statusDTORequest;
  }

  public void setIdPositions(List<String> idPositions) {
    this.idPositions = idPositions;
  }

  public void setStatusDTORequest(String statusDTORequest) {
    this.statusDTORequest = statusDTORequest;
  }

}
