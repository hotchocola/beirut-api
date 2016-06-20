package com.gdn.x.beirut.services;

import com.gdn.x.beirut.entities.Candidate;

public interface EventService {
  public Candidate insertNewCandidateDenormalized(Candidate candidate);
}
