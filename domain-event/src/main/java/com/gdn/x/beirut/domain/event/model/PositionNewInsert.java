package com.gdn.x.beirut.domain.event.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gdn.common.base.entity.GdnBaseDomainEventModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionNewInsert extends GdnBaseDomainEventModel {
  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
