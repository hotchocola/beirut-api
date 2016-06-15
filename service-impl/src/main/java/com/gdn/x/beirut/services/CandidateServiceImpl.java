package com.gdn.x.beirut.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.x.beirut.dao.CandidateDAO;
import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;
import com.gdn.x.beirut.entities.StatusLog;

@Service(value = "candidateService")
@Transactional(readOnly = true)
public class CandidateServiceImpl implements CandidateService {

  private static final Logger LOG = LoggerFactory.getLogger(CandidateServiceImpl.class);
  private static final String ID_SHOULD_EMPTY_FOR_NEW_RECORD = "id should empty for new record";
  private static final String ID_SHOULD_NOT_BE_EMPTY = "id should not be empty";

  @Autowired
  private CandidateDAO candidateDAO;

  @Autowired
  private PositionDAO positionDAO;

  @Override
  @Transactional(readOnly = false)
  public void applyNewPosition(Candidate candidate, Position position) throws Exception {
    Candidate existingCandidate = getCandidate(candidate.getId());
    existingCandidate.getCandidatePositions().add(new CandidatePosition(candidate, position));
    candidateDAO.save(existingCandidate);
  }

  @Override
  @Transactional(readOnly = false)
  public Candidate createNew(Candidate candidate, Position position) {
    CandidatePosition candidatePosition = new CandidatePosition();
    candidatePosition.setCandidate(candidate);
    candidatePosition.setPosition(position);
    candidate.getCandidatePositions().add(candidatePosition);
    position.getCandidatePositions().add(candidatePosition);
    return candidateDAO.save(candidate);
  }

  // public Candidate getAllCandidatePositionStatus(String id) {
  // Candidate candidate = candidateDAO.findOne(id);
  // Hibernate.initialize(candidate.getCandidatePositions());
  //
  // return candidate;
  // }

  @Override
  public List<Candidate> getAllCandidates() {
    return candidateDAO.findAll();
  }

  @Override
  public Page<Candidate> getAllCandidatesWithPageable(Pageable pageable) {
    return candidateDAO.findAll(pageable);
  }

  @Override
  public Candidate getCandidate(String id) throws Exception {
    Candidate candidate = candidateDAO.findOne(id);
    if (candidate == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND);
    } else {
      return candidate;
    }
  }

  public CandidateDAO getCandidateDAO() {
    return candidateDAO;
  }

  @Override
  public CandidateDetail getCandidateDetail(String id) throws Exception {
    Candidate candidate = getCandidate(id);
    Hibernate.initialize(candidate.getCandidateDetail());
    return candidate.getCandidateDetail();
  }

  public PositionDAO getPositionDAO() {
    return positionDAO;
  }

  // Bulk Delete
  @Override
  @Transactional(readOnly = false)
  public void markForDelete(List<String> ids) throws Exception {
    System.out.println(ids.toString());
    for (int i = 0; i < ids.size(); i++) {
      markForDelete(ids.get(i));
    }
  }

  @Override
  public void markForDelete(String id) throws Exception {
    Candidate candidate = this.candidateDAO.findByIdAndMarkForDelete(id, false);
    Hibernate.initialize(candidate.getCandidatePositions());
    Iterator<CandidatePosition> iterator = candidate.getCandidatePositions().iterator();
    while (iterator.hasNext()) {
      CandidatePosition candidatePositon = iterator.next();
      candidatePositon.setMarkForDelete(true);
      Hibernate.initialize(candidatePositon.getStatusLogs());
      candidatePositon.getStatusLogs().stream().forEach(statusLog -> {
        statusLog.setMarkForDelete(true);
      });
    }
    candidate.setMarkForDelete(true);
    this.candidateDAO.save(candidate);
  }

  @Override
  public List<Candidate> searchByCreatedDateBetween(Date start, Date end) {
    return candidateDAO.findByCreatedDateBetween(start, end);
  }

  @Override
  public List<Candidate> searchByFirstName(String firstname) {
    return candidateDAO.findByFirstNameLike(firstname);
  }

  @Override
  public List<Candidate> searchByLastName(String lastname) {
    return candidateDAO.findByLastNameLike(lastname);
  }

  @Override
  public List<Candidate> searchCandidateByEmailAddress(String emailAddress) {
    return candidateDAO.findByEmailAddress(emailAddress);
  }

  @Override
  public List<Candidate> searchCandidateByPhoneNumber(String phoneNumber) {
    return candidateDAO.findByPhoneNumber(phoneNumber);
  }

  @Override
  public List<Candidate> searchCandidateByPhoneNumberLike(String phoneNumber) {
    return candidateDAO.findByPhoneNumberLike(phoneNumber);
  }

  public void setCandidateDAO(CandidateDAO candidateDAO) {
    this.candidateDAO = candidateDAO;
  }

  // @Override
  // public boolean setCandidatePositionStatus(String idCandidatePosition, Status newStatus) {
  // // TODO Auto-generated method stub
  // // 1. insert new status log...
  // // 2. update status candidateposition (yang saat ini)
  // CandidatePosition candidatePostition =
  // candidateDAO.findCandidatePositionById(idCandidatePosition);
  // StatusLog newStatusLog = new StatusLog();
  // newStatusLog.setStatus(newStatus);
  // newStatusLog.setCandidatePositions(candidatePostition);
  // return false;
  // }

  public void setPositionDAO(PositionDAO positionDAO) {
    this.positionDAO = positionDAO;
  }

  @Override
  @Transactional(readOnly = false)
  public void updateCandidateDetail(Candidate candidate) throws Exception {
    if (candidate.getId() != null) {
      Candidate existingCandidate = getCandidate(candidate.getId());
      existingCandidate.setFirstName(candidate.getFirstName());
      existingCandidate.setLastName(candidate.getLastName());
      existingCandidate.setEmailAddress(candidate.getEmailAddress());
      existingCandidate.setPhoneNumber(candidate.getPhoneNumber());
      Hibernate.initialize(candidate.getCandidateDetail());
      CandidateDetail existingDetail = existingCandidate.getCandidateDetail();
      existingDetail.setContent(candidate.getCandidateDetail().getContent());
      candidateDAO.save(candidate);
    } else {
      throw new ApplicationException(ErrorCategory.VALIDATION, ID_SHOULD_NOT_BE_EMPTY);
    }
  }

  @Override
  @Transactional(readOnly = false)
  public void updateCandidateStatus(Candidate candidate, Position position, Status status)
      throws Exception {
    Candidate existingCandidate = getCandidate(candidate.getId());
    Position existingPosition = positionDAO.findOne(position.getId());
    Hibernate.initialize(existingCandidate.getCandidatePositions());
    existingCandidate.getCandidatePositions().stream()
        .filter(candidatePosition -> candidatePosition.getPosition().equals(existingPosition))
        .forEach(candidatePosition -> {
          candidatePosition.getStatusLogs().add(new StatusLog(candidatePosition, status));
          candidatePosition.setStatus(status); // add missing setter zal
        });
    candidateDAO.save(existingCandidate);
  }

  @Override
  @Transactional(readOnly = false)
  public void updateCandidateStatusBulk(List<Candidate> candidates, Position position,
      Status status) throws Exception {
    candidates.stream().forEach(candidate -> {
      try {
        this.updateCandidateStatus(candidate, position, status);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    });
  }



}
