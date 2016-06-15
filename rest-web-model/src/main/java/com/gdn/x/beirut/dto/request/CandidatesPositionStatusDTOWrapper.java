package com.gdn.x.beirut.dto.request;

import java.util.List;

import com.gdn.common.web.base.BaseRequest;

import ch.qos.logback.core.status.Status;

public class CandidatesPositionStatusDTOWrapper extends BaseRequest {

  private List<CandidateDTORequest> candidates;

  private PositionDTORequest position;

  private Status status;

}
