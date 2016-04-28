package com.gdn.x.beirut.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Candidate;


public interface CandidateDAO extends JpaRepository<Candidate, String> {
  List<Candidate> findByCreatedDateBetween(Date start, Date end);

  List<Candidate> findByEmailaddress(String emailaddress);

  List<Candidate> findByFirstname(String firstname);

  List<Candidate> findByFirstnameLike(String firstname);

  List<Candidate> findByLastname(String lastname);

  List<Candidate> findByLastnameLike(String lastname);

  List<Candidate> findByPhonenumber(String phonenumber);
}
