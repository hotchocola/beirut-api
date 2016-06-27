package com.gdn.x.beirut.services;

import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.publisher.PublishDomainEvent;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.domain.event.model.PositionMarkForDelete;
import com.gdn.x.beirut.entities.Position;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {

  @Override
  @PublishDomainEvent(publishEventClass = PositionMarkForDelete.class,
      domainEventName = DomainEventName.POSITION_MARK_FOR_DELETE)
  public Position markForDelete(Position position) {
    return position;
  }

}
