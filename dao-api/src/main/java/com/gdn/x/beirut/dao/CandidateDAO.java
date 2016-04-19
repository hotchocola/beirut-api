package com.gdn.x.beirut.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Candidate;


public interface CandidateDAO extends JpaRepository<Candidate, String> {
  List<Candidate> findByEmailaddress(String emailaddress);

  List<Candidate> findByFirstname(String firstname);

  List<Candidate> findByLastname(String lastname);

  List<Candidate> findByPhonenumber(String phonenumber);
}
