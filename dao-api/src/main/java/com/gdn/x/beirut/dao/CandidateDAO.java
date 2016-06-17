package com.gdn.x.beirut.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gdn.x.beirut.entities.Candidate;

public interface CandidateDAO extends JpaRepository<Candidate, String> {

  List<Candidate> findByCreatedDateBetweenAndStoreId(Date start, Date end, String storeId);

  List<Candidate> findByEmailAddressAndStoreId(String emailAddress, String storeId);

  List<Candidate> findByFirstName(String firstName);

  List<Candidate> findByFirstNameContaining(String firstName);

  Candidate findByIdAndMarkForDelete(String id, boolean markForDelete);

  List<Candidate> findByLastName(String lastName);

  List<Candidate> findByLastNameContainingAndStoreId(String lastName, String storeId);

  List<Candidate> findByPhoneNumber(String phoneNumber);

  List<Candidate> findByPhoneNumberContainingAndStoreId(String phoneNumber, String storeId);

  List<Candidate> findByStoreId(String storeId);

  Page<Candidate> findByStoreId(String storeId, Pageable pageable);

  List<Candidate> findByStoreIdAndMarkForDelete(String storeId, boolean markForDelete);

}
