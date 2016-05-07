package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class PositionDTOResponse extends BaseResponse {
  private String title;
<<<<<<< HEAD
  private boolean markForDelete;

  private Set<CandidatePositionDTOResponse> candposdto;

  public Set<CandidatePositionDTOResponse> getCandposdto() {
    return candposdto;
  }
=======
>>>>>>> refs/remotes/bliblidotcom/develop

  public String getTitle() {
    return title;
  }

<<<<<<< HEAD
  public boolean isMarkForDelete() {
    return markForDelete;
  }

  public void setCandidatePositionDTOResponse(CandidatePositionDTOResponse candposdto) {
    this.candposdto.add(candposdto);
  }

  public void setCandidatePositionManual(CandidatePositionDTOResponse candpos) {
    this.candposdto.add(candpos);
  }

  public void setCandreqs(Set<CandidatePositionDTOResponse> candposdto) {
    this.candposdto = candposdto;
  }

  public void setMarkForDelete(boolean markForDelete) {
    this.markForDelete = markForDelete;
  }

=======
>>>>>>> refs/remotes/bliblidotcom/develop
  public void setTitle(String title) {
    this.title = title;
  }
}
