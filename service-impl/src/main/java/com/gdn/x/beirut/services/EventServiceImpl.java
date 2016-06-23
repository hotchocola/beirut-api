package com.gdn.x.beirut.services;

import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.publisher.PublishDomainEvent;
import com.gdn.x.beirut.domain.event.model.CandidateUpdateStatus;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.domain.event.model.PositionNewInsert;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {

  @Override
  @PublishDomainEvent(publishEventClass = PositionNewInsert.class,
      domainEventName = DomainEventName.POSITION_NEW_INSERT)
  public Position insertNewPosition(Position position) {
    return position;
  }

  @Override
  @PublishDomainEvent(publishEventClass = CandidateUpdateStatus.class,
      domainEventName = DomainEventName.CANDIDATE_UPDATE_STATUS)
  public Candidate updateCandidateStatus(String storeid, String candidateId, String idPosition,
      Status status) {
    return new Candidate();
  }

}
