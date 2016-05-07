package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class PositionDTORequest extends BaseRequest {

  private String title;
  private String id;

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
