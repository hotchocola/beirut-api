package com.gdn.x.beirut.services;

import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.publisher.PublishDomainEvent;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.domain.event.model.PositionNewInsert;
import com.gdn.x.beirut.entities.Position;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {

  @Override
  @PublishDomainEvent(publishEventClass = PositionNewInsert.class,
      domainEventName = DomainEventName.POSITION_NEW_INSERT)
  public Position insertNewPosition(Position position) {
    return position;
  }

}
