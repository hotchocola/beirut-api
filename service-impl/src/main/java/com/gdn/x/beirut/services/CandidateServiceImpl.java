package com.gdn.x.beirut.services;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.x.beirut.dao.CandidateDAO;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;

@Service(value = "candidateService")
@Transactional(readOnly = true)
public class CandidateServiceImpl implements CandidateService {

  @Autowired
  CandidateDAO candidateDao;


  @Override
  public List<Candidate> getAllCandidateDetailStatus() {
    List<Candidate> candidates = getAllCandidates();
    for (Candidate candidate : candidates) {
      Hibernate.initialize(candidate.getCandidatePositions());
    }
    return candidates;
  }

  @Override
  public List<Candidate> getAllCandidates() {
    return this.candidateDao.findAll();
  }



  @Override
  public Candidate getCandidate(String id) throws Exception {
    // TODO Auto-generated method stub
    final Candidate candidate = this.candidateDao.findOne(id);
    if (candidate == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND);
    } else {
      return candidate;
    }
  }

  @Override
  public CandidateDetail getCandidateDetail(String id) throws Exception {
    // TODO Auto-generated method stub
    final Candidate candidate = this.candidateDao.findOne(id);
    if (candidate == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND);
    } else {
      return candidate.getCandidateDetail();
    }
  }

  @Override
  @Transactional(readOnly = false)
  public void markForDelete(String id) {
    // TODO Auto-generated method stub
    final Candidate candidate = this.candidateDao.findOne(id);
    candidate.setMarkForDelete(true);
    this.candidateDao.save(candidate);
  }

  @Override
  @Transactional(readOnly = false)
  public Candidate save(Candidate candidate) {
    return this.candidateDao.save(candidate);
  }

  @Override
  public List<Candidate> searchByCreatedDateBetween(Date start, Date end) {
<<<<<<< HEAD
    // TODO Auto-generated method stub
    return this.candidateDao.findByCreatedDateBetween(start, end);
=======
    // TODO Auto-generated method stub
    return this.candidateDao.findByCreatedDateBetween(start, end);
  }

  @Override
  public List<Candidate> searchByFirstname(String firstname) {
    // TODO Auto-generated method stub
    return this.candidateDao.findByFirstnameLike(firstname);
>>>>>>> refs/remotes/bliblidotcom/develop
  }

  @Override
  public List<Candidate> searchByFirstName(String firstname) {
    // TODO Auto-generated method stub
<<<<<<< HEAD
    return this.candidateDao.findByFirstNameLike(firstname);
  }

  @Override
  public List<Candidate> searchByLastName(String lastname) {
    // TODO Auto-generated method stub
    return this.candidateDao.findByLastNameLike(lastname);
=======
    return this.candidateDao.findByLastnameLike(lastname);
>>>>>>> refs/remotes/bliblidotcom/develop
  }

  @Override
  public List<Candidate> searchCandidateByEmailAddress(String emailAddress) {
    // TODO Auto-generated method stub
<<<<<<< HEAD
    return this.candidateDao.findByEmailAddress(emailAddress);
=======
    return this.candidateDao.findByEmailaddress(emailAddress);
>>>>>>> refs/remotes/bliblidotcom/develop
  }

  @Override
  public List<Candidate> searchCandidateByPhoneNumber(String phoneNumber) {
    // TODO Auto-generated method stub
<<<<<<< HEAD
    return this.candidateDao.findByPhoneNumber(phoneNumber);
=======
    return this.candidateDao.findByPhonenumber(phoneNumber);
>>>>>>> refs/remotes/bliblidotcom/develop
  }

  @Override
  public List<Candidate> searchCandidateByPhoneNumberLike(String phoneNumber) {
    // TODO Auto-generated method stub
<<<<<<< HEAD
    return this.candidateDao.findByPhoneNumberLike(phoneNumber);
=======
    return this.candidateDao.findByPhonenumberLike(phoneNumber);
>>>>>>> refs/remotes/bliblidotcom/develop
  }

  @Override
  @Transactional(readOnly = false)
  public void setCandidateDetail(String id, CandidateDetail candidateDetail) throws Exception {
    // TODO Auto-generated method stub
    final Candidate candidate = this.candidateDao.findOne(id);
    if (candidate == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND);
    } else {
      candidate.setCandidateDetail(candidateDetail);
      this.candidateDao.save(candidate);
    }
  }

  // @Override
  // public boolean setCandidatePositionStatus(String idCandidatePosition, Status newStatus) {
  // // TODO Auto-generated method stub
  // // 1. insert new status log...
  // // 2. update status candidateposition (yang saat ini)
  // CandidatePosition candidatePostition =
  // this.candidateDao.findCandidatePositionById(idCandidatePosition);
  // StatusLog newStatusLog = new StatusLog();
  // newStatusLog.setStatus(newStatus);
  // newStatusLog.setCandidatePosition(candidatePostition);
  // return false;
  // }

}
