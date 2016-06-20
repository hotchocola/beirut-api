package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class StatusLogDTOResponse extends BaseResponse {

  private static final long serialVersionUID = -1283231815109258028L;

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
