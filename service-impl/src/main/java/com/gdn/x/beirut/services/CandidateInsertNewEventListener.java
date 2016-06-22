package com.gdn.x.beirut.services;

import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEvent;
import com.gdn.x.beirut.domain.event.model.CandidateNewInsert;
import com.gdn.x.beirut.domain.event.model.DomainEventName;

@Service
@SubscribeDomainEvent(DomainEventName.CANDIDATE_NEW_INSERT)
public class CandidateInsertNewEventListener implements DomainEventListener<CandidateNewInsert> {

  @Override
  public void onDomainEventConsumed(CandidateNewInsert message) throws Exception {
    System.out.print("[ " + message.getEmailAddress() + ", ");
    System.out.print(message.getFirstName() + ", ");
    System.out.print(message.getLastName() + ", ");
    System.out.print(message.getPhoneNumber() + ", ");
    System.out.print(message.getStatus() + ", ");
    System.out.print(message.getTimestamp() + " ]");
  }

}
