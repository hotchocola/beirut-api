package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class PositionDTOResponse extends BaseResponse {
  private String id;
  private String title;
  private boolean markForDelete;

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
