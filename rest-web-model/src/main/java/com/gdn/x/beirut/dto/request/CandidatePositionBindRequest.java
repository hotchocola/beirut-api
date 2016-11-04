package com.gdn.x.beirut.dto.request;

import java.io.Serializable;

public class CandidatePositionBindRequest implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 7669251463570015010L;

  private String idCandidate;

  private String idPosition;

  public String getIdCandidate() {
    return idCandidate;
  }

  public String getIdPosition() {
    return idPosition;
  }

  public void setIdCandidate(String idCandidate) {
    this.idCandidate = idCandidate;
  }

  public void setIdPosition(String idPosition) {
    this.idPosition = idPosition;
  }

  @Override
  public String toString() {
    return "[idCandidate=" + idCandidate + ", idPosition=" + idPosition + "]";
  }
}
