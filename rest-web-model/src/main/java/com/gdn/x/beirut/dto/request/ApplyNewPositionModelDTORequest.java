package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

public class ApplyNewPositionModelDTORequest extends BaseRequest {

  private String idCandidate;

  private List<String> listPositionIds;

  public String getIdCandidate() {
    return idCandidate;
  }

  public List<String> getListPositionIds() {
    return listPositionIds;
  }

  public void setIdCandidate(String idCandidate) {
    this.idCandidate = idCandidate;
  }

  public void setListPositionIds(List<String> listPositionIds) {
    this.listPositionIds = listPositionIds;
  }

}
