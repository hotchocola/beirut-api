package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;
<<<<<<< HEAD

public class CandidateDetailDTOResponse extends BaseResponse {
  private byte[] content;

  public CandidateDetailDTOResponse() {
    super();
  }

  public CandidateDetailDTOResponse(String STORE_ID) {
    super();
    this.setStoreId(STORE_ID);
=======
import com.gdn.x.beirut.dto.request.CandidateDTORequest;

public class CandidateDetailDTOResponse extends BaseResponse {
  private CandidateDTORequest candidate;
  private byte[] content;

  public CandidateDTORequest getCandidate() {
    return candidate;
>>>>>>> refs/remotes/bliblidotcom/develop
  }

  public byte[] getContent() {
    return content;
  }

<<<<<<< HEAD
=======
  public void setCandidate(CandidateDTORequest candidate) {
    this.candidate = candidate;
  }

>>>>>>> refs/remotes/bliblidotcom/develop
  public void setContent(byte[] content) {
    this.content = content;
  }

}
