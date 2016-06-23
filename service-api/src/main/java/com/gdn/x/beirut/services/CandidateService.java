package com.gdn.x.beirut.services;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Status;

public interface CandidateService {

  Candidate applyNewPosition(String candidateId, List<String> positionId) throws Exception;

  Candidate createNew(Candidate candidate, List<String> positionIds) throws Exception;

  // Page<CandidatePosition> getAllCandidatePositionByStoreId(String storeId, Pageable pageable);

  List<Candidate> getAllCandidates();

  Page<Candidate> getAllCandidatesByStoreIdAndMarkForDeletePageable(String storeId,
      boolean markForDelete, Pageable pageable) throws Exception;

  Page<Candidate> getAllCandidatesByStoreIdPageable(String storeId, Pageable pageable)
      throws Exception;

  Page<Candidate> getAllCandidatesWithPageable(String storeId, Pageable pageable);

  Candidate getCandidate(String id) throws Exception;

  Candidate getCandidateByIdAndStoreIdEager(String id, String storeId) throws Exception;

  Candidate getCandidateByIdAndStoreIdLazy(String id, String storeId) throws Exception;

  CandidateDetail getCandidateDetailAndStoreId(String id, String storeId) throws Exception;

  CandidatePosition getCandidatePositionByStoreIdWithLogs(String idCandidate, String idPosition,
      String storeId) throws Exception;

  void markForDelete(List<String> ids) throws Exception;

  void markForDelete(String id) throws Exception;

  Page<Candidate> searchByCreatedDateBetweenAndStoreId(Date start, Date end, String storeId,
      Pageable pageable);

  Page<Candidate> searchByFirstNameContainAndStoreId(String firstname, String storeId,
      Pageable pageable) throws Exception;

  Page<Candidate> searchByLastNameContainAndStoreId(String lastName, String storeId,
      Pageable pageable);

  Candidate searchCandidateByEmailAddressAndStoreId(String emailAddress, String storeId);

  List<Candidate> searchCandidateByPhoneNumber(String phoneNumber);

  Page<Candidate> searchCandidateByPhoneNumberContainAndStoreId(String phoneNumber, String storeId,
      Pageable pageable);

  void updateCandidateDetail(String storeId, Candidate candidate) throws Exception;

  void updateCandidateStatus(String storeid, String candidateId, String idPosition, Status status)
      throws Exception;

  void updateCandidateStatusBulk(String storeId, List<String> idCandidates, String idPosition,
      Status status) throws Exception;
}
