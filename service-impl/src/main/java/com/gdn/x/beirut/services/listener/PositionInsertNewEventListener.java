package com.gdn.x.beirut.services.listener;

import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEvent;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.domain.event.model.PositionNewInsert;

@Service
@SubscribeDomainEvent(DomainEventName.POSITION_NEW_INSERT)
public class PositionInsertNewEventListener implements DomainEventListener<PositionNewInsert> {


  @Override
  public void onDomainEventConsumed(PositionNewInsert message) throws Exception {

  }

}
