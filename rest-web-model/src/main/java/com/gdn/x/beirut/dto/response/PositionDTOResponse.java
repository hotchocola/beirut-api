package com.gdn.x.beirut.dto.response;

<<<<<<< HEAD
=======
import java.util.Set;

>>>>>>> refs/remotes/bliblidotcom/develop
import com.gdn.common.web.base.BaseResponse;

public class PositionDTOResponse extends BaseResponse {
  private String id;
  private String title;
  private boolean markForDelete;

<<<<<<< HEAD
=======
  private Set<CandidatePositionDTORequest> candreqs;

  public Set<CandidatePositionDTORequest> getCandreqs() {
    return candreqs;
  }

>>>>>>> refs/remotes/bliblidotcom/develop
  @Override
  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public boolean isMarkForDelete() {
    return markForDelete;
  }

<<<<<<< HEAD
=======
  public void setCandreqs(Set<CandidatePositionDTORequest> candreqs) {
    this.candreqs = candreqs;
  }

>>>>>>> refs/remotes/bliblidotcom/develop
  @Override
  public void setId(String id) {
    this.id = id;
  }

  public void setMarkForDelete(boolean markForDelete) {
    this.markForDelete = markForDelete;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
