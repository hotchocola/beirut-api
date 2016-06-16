package com.gdn.x.beirut.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = Candidate.TABLE_NAME)
public class Candidate extends GdnBaseEntity {

  public static final String TABLE_NAME = "candidate";
  public static final String COLUMN_EMAIL_ADDRESS = "email_address";
  public static final String COLUMN_FIRST_NAME = "first_name";
  public static final String COLUMN_LAST_NAME = "last_name";
  public static final String COLUMN_PHONE_NUMBER = "phone_number";

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidate")
  private CandidateDetail candidateDetail;

  @Column(name = Candidate.COLUMN_EMAIL_ADDRESS)
  private String emailAddress;

  @Column(name = Candidate.COLUMN_FIRST_NAME)
  private String firstName;

  @Column(name = Candidate.COLUMN_LAST_NAME)
  private String lastName;

  @Column(name = Candidate.COLUMN_PHONE_NUMBER)
  private String phoneNumber;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidate")
  private Set<CandidatePosition> candidatePositions = new HashSet<CandidatePosition>();

  public Candidate() {
    // nothing to do here
    // setId(UUID.randomUUID().toString());
  }

  public CandidateDetail getCandidateDetail() {
    return candidateDetail;
  }

  public Set<CandidatePosition> getCandidatePositions() {
    return candidatePositions;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setCandidateDetail(CandidateDetail candidateDetail) {
    this.candidateDetail = candidateDetail;
  }

  public void setCandidatePositions(Set<CandidatePosition> candidatePositions) {
    this.candidatePositions = candidatePositions;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
