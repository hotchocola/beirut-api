package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

public class CandidatesPositionDTOWrapper extends BaseRequest {

  private List<String> idCandidates;

  private PositionDTORequest position;

  public CandidatesPositionDTOWrapper() {

  }

  public List<String> getIdCandidates() {
    return idCandidates;
  }

  public PositionDTORequest getPosition() {
    return position;
  }

  public void setIdCandidates(List<String> idCandidates) {
    this.idCandidates = idCandidates;
  }

  public void setPosition(PositionDTORequest position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return "CandidatesPositionDTOWrapper [idCandidates=" + idCandidates + ", position=" + position
        + "]";
  }


}
