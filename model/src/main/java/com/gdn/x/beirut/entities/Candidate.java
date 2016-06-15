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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    Candidate other = (Candidate) obj;
    if (candidatePositions == null) {
      if (other.candidatePositions != null)
        return false;
    } else if (!candidatePositions.equals(other.candidatePositions))
      return false;
    if (emailAddress == null) {
      if (other.emailAddress != null)
        return false;
    } else if (!emailAddress.equals(other.emailAddress))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.equals(other.lastName))
      return false;
    if (phoneNumber == null) {
      if (other.phoneNumber != null)
        return false;
    } else if (!phoneNumber.equals(other.phoneNumber))
      return false;
    return true;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((candidatePositions == null) ? 0 : candidatePositions.hashCode());
    result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    return result;
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
