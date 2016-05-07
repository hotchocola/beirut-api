package com.gdn.x.beirut.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidatePosition;


public interface CandidateDAO extends JpaRepository<Candidate, String> {
  List<CandidatePosition> findAllCandidatePositions();

  List<Candidate> findByCreatedDateBetween(Date start, Date end);

  List<Candidate> findByEmailaddress(String emailaddress);

  List<Candidate> findByFirstname(String firstname);

  List<Candidate> findByFirstnameLike(String firstname);

  List<Candidate> findByLastname(String lastname);

  List<Candidate> findByLastnameLike(String lastname);

  Candidate findByPhonenumber(String phonenumber);

  List<Candidate> findByPhonenumberLike(String phonenumber);
  // @Query("from CandidatePosition where id = :id")
  // CandidatePosition findCandidatePositionById(@[param("id") String id);
  //
  // }
}
