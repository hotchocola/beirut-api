package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class InsertNewCandidateDTORequest extends BaseRequest {

  private static final long serialVersionUID = -2049806947227907761L;

  private String candidateDTORequestString;

  private CandidateDetailDTORequest candidateDetailDTORequest;

  public CandidateDetailDTORequest getCandidateDetailDTORequest() {
    return candidateDetailDTORequest;
  }

  public String getCandidateDTORequestString() {
    return candidateDTORequestString;
  }

  public void setCandidateDetailDTORequest(CandidateDetailDTORequest candidateDetailDTORequest) {
    this.candidateDetailDTORequest = candidateDetailDTORequest;
  }

  public void setCandidateDTORequestString(String candidateDTORequestString) {
    this.candidateDTORequestString = candidateDTORequestString;
  }


}
