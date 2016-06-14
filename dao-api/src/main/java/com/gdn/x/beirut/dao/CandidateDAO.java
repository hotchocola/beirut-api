package com.gdn.x.beirut.dao;

import com.gdn.x.beirut.entities.Candidate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CandidateDAO extends JpaRepository<Candidate, String> {

  List<Candidate> findByCreatedDateBetween(Date start, Date end);

  List<Candidate> findByEmailAddress(String emailAddress);

  List<Candidate> findByFirstName(String firstName);

  List<Candidate> findByFirstNameLike(String firstName);

  List<Candidate> findByLastName(String lastName);

  List<Candidate> findByLastNameLike(String lastName);

  List<Candidate> findByPhoneNumber(String phoneNumber);

  List<Candidate> findByPhoneNumberLike(String phoneNumber);
}
