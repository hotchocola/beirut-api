package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

public class UpdateCandidateStatusModelDTORequest extends BaseRequest {

  private static final long serialVersionUID = -4152825937934296238L;

  private String statusDTORequest;

  private List<CandidatePositionBindRequest> listBind;


  public List<CandidatePositionBindRequest> getListBind() {
    return listBind;
  }

  public String getStatusDTORequest() {
    return statusDTORequest;
  }

  public void setListBind(List<CandidatePositionBindRequest> listBind) {
    this.listBind = listBind;
  }

  public void setStatusDTORequest(String statusDTORequest) {
    this.statusDTORequest = statusDTORequest;
  }

}
