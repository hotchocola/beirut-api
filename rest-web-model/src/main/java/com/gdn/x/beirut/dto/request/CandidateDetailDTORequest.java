package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class CandidateDetailDTORequest extends BaseRequest {

  private CandidateDTORequest candidate;
  private byte[] content;

  public CandidateDTORequest getCandidate() {
    return candidate;
  }

  public byte[] getContent() {
    return content;
  }

  public void setCandidate(CandidateDTORequest candidate) {
    this.candidate = candidate;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }
}
