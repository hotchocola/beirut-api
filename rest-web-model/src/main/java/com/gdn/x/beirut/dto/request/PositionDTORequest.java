package com.gdn.x.beirut.dto.request;

import java.util.Set;

import com.gdn.common.web.base.BaseRequest;

public class PositionDTORequest extends BaseRequest {

  private String title;

  private Set<CandidatePositionDTORequest> candpos;

  public Set<CandidatePositionDTORequest> getCandpos() {
    return candpos;
  }

  public String getTitle() {
    return title;
  }

  public void setCandpos(Set<CandidatePositionDTORequest> candpos) {
    this.candpos = candpos;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
