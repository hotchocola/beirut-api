package com.gdn.x.beirut.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.publisher.PublishDomainEvent;
import com.gdn.x.beirut.domain.event.model.CandidateNewInsert;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;

@Service(value = "EventService")
public class EventServiceImpl implements EventService {

  @Override
  public Candidate insertNewCandidateDenormalized(Candidate candidate) {
    Set<CandidatePosition> candidatePositions = candidate.getCandidatePositions();
    List<CandidateNewInsert> candidateNewInserts = new ArrayList<>();
    for (CandidatePosition candidatePosition : candidatePositions) {
      CandidateNewInsert candidateNewInsert = new CandidateNewInsert();
      BeanUtils.copyProperties(candidate, candidateNewInsert, "candidateDetail",
          "candidatePositions");
      Position position = candidatePosition.getPosition();
      BeanUtils.copyProperties(position, candidateNewInsert, "candidatePositions");
      candidateNewInsert.setStatus(candidatePosition.getStatus().toString());
      insertNewCandidateSingle(candidateNewInsert);
    }
    return candidate;
  }

  @PublishDomainEvent(publishEventClass = CandidateNewInsert.class,
      domainEventName = DomainEventName.CANDIDATE_NEW_INSERT)
  public CandidateNewInsert insertNewCandidateSingle(CandidateNewInsert candidateNewInsert) {
    return candidateNewInsert;
  }
}
