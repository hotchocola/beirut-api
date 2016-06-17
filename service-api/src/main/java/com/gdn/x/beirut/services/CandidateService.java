package com.gdn.x.beirut.services;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;

public interface CandidateService {
  void applyNewPosition(Candidate candidate, Position position) throws Exception;

  Candidate createNew(Candidate candidate, Position position) throws Exception;

  List<Candidate> getAllCandidates();

  List<Candidate> getAllCandidatesByStoreId(String storeId) throws Exception;

  Page<Candidate> getAllCandidatesWithPageable(String storeId, Pageable pageable);

  Candidate getCandidate(String id) throws Exception;

  Candidate getCandidateByIdAndStoreIdEager(String id, String storeId) throws Exception;

  Candidate getCandidateByIdAndStoreIdLazy(String id, String storeId) throws Exception;

  CandidateDetail getCandidateDetailAndStoreId(String id, String storeId) throws Exception;

  CandidatePosition getCandidatePositionWithLogs(String idCandidate, String idPosition)
      throws Exception;

  void markForDelete(List<String> ids) throws Exception;

  void markForDelete(String id) throws Exception;

  List<Candidate> searchByCreatedDateBetweenAndStoreId(Date start, Date end, String storeId);

  List<Candidate> searchByFirstNameContainAndStoreId(String firstname, String storeId)
      throws Exception;

  List<Candidate> searchByLastNameContainAndStoreId(String lastname, String storeId);

  List<Candidate> searchCandidateByEmailAddressAndStoreId(String emailAddress, String storeId);

  List<Candidate> searchCandidateByPhoneNumber(String phoneNumber);

  List<Candidate> searchCandidateByPhoneNumberContainAndStoreId(String phoneNumber, String storeId);

  void updateCandidateDetail(String storeId, Candidate candidate) throws Exception;

  void updateCandidateStatus(String storeid, Candidate candidate, String idPosition, Status status)
      throws Exception;

  void updateCandidateStatusBulk(String storeId, List<String> idCandidates, String idPosition,
      Status status) throws Exception;
}
