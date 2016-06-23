package com.gdn.x.beirut.services;

import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;

public interface EventService {
  public Position insertNewPosition(Position position);

  public Candidate updateCandidateStatus(String storeid, String candidateId, String idPosition,
      Status status);
}
