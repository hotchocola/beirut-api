package com.gdn.x.beirut.dto.request;

<<<<<<< HEAD
=======
import java.util.Set;

>>>>>>> refs/remotes/bliblidotcom/develop
import com.gdn.common.web.base.BaseRequest;

public class PositionDTORequest extends BaseRequest {
  private String title;

<<<<<<< HEAD
=======
  private Set<CandidatePositionDTORequest> candpos;

  public Set<CandidatePositionDTORequest> getCandpos() {
    return candpos;
  }

>>>>>>> refs/remotes/bliblidotcom/develop
  public String getTitle() {
    return title;
  }

<<<<<<< HEAD
=======
  public void setCandpos(Set<CandidatePositionDTORequest> candpos) {
    this.candpos = candpos;
  }

>>>>>>> refs/remotes/bliblidotcom/develop
  public void setTitle(String title) {
    this.title = title;
  }
}
