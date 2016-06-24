package com.gdn.x.beirut.services;

import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.publisher.PublishDomainEvent;
import com.gdn.x.beirut.domain.event.model.CandidateUpdateStatus;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.domain.event.model.PositionMarkForDelete;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {

  @Override
  public Position insertNewPosition(Position position) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  @PublishDomainEvent(publishEventClass = PositionMarkForDelete.class,
      domainEventName = DomainEventName.POSITION_MARK_FOR_DELETE)
  public Position markForDelete(Position position) {
    return position;
  }

  @Override
  @PublishDomainEvent(publishEventClass = CandidateUpdateStatus.class,
      domainEventName = DomainEventName.CANDIDATE_UPDATE_STATUS)
  public Candidate updateCandidateStatus(Candidate candidate, Position position, Status status) {
    return candidate;
  }

}
