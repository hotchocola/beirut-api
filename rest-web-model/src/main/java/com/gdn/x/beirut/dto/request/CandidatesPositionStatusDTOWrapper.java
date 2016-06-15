package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

public class CandidatesPositionStatusDTOWrapper extends BaseRequest {

  private List<CandidateDTORequest> candidates;

  private PositionDTORequest position;

  private StatusDTORequest status;

  public CandidatesPositionStatusDTOWrapper() {

  }

  public List<CandidateDTORequest> getCandidates() {
    return candidates;
  }

  public PositionDTORequest getPosition() {
    return position;
  }

  public StatusDTORequest getStatus() {
    return status;
  }

  public void setCandidates(List<CandidateDTORequest> candidates) {
    this.candidates = candidates;
  }

  public void setPosition(PositionDTORequest position) {
    this.position = position;
  }

  public void setStatus(StatusDTORequest status) {
    this.status = status;
  }



}
