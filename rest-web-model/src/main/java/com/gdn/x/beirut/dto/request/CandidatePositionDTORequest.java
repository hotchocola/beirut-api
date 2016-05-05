package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class CandidatePositionDTORequest extends BaseRequest {
  //untuk Position sementara.
//  private PositionDTORequest posreq;
//
//  public PositionDTORequest getPosreq() {
//    return posreq;
//  }
//
//  public void setPosreq(PositionDTORequest posreq) {
//    this.posreq = posreq;
//  }

  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


}
