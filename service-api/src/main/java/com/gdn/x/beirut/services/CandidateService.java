package com.gdn.x.beirut.services;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;

public interface CandidateService {
  void applyNewPosition(Candidate candidate, Position position) throws Exception;

  Candidate createNew(Candidate candidate, Position position) throws Exception;

  List<Candidate> getAllCandidateDetailStatus();

  List<Candidate> getAllCandidates();

  Page<Candidate> getAllCandidatesWithPageable(Pageable pageable);

  Candidate getCandidate(String id) throws Exception;

  CandidateDetail getCandidateDetail(String id) throws Exception;

  void markForDelete(String id) throws Exception;

  List<Candidate> searchByCreatedDateBetween(Date start, Date end);

  List<Candidate> searchByFirstName(String firstname);

  List<Candidate> searchByLastName(String lastname);

  List<Candidate> searchCandidateByEmailAddress(String emailAddress);

  List<Candidate> searchCandidateByPhoneNumber(String phoneNumber);

  List<Candidate> searchCandidateByPhoneNumberLike(String phoneNumber);

  void updateCandidateDetail(Candidate candidate) throws Exception;

  void updateCandidateStatus(Candidate candidate, Position position, Status status)
      throws Exception;
}
