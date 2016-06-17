package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class StatusLogDTOResponse extends BaseResponse {

  private StatusDTOResponse status;

  private CandidatePositionDTOResponse candidatePosition;

  public CandidatePositionDTOResponse getCandidatePosition() {
    return candidatePosition;
  }

  public StatusDTOResponse getStatus() {
    return status;
  }

  public void setCandidatePosition(CandidatePositionDTOResponse candidatePosition) {
    this.candidatePosition = candidatePosition;
  }

  public void setStatus(StatusDTOResponse status) {
    this.status = status;
  }

}
