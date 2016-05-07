package com.gdn.x.beirut.dto.response;

import java.util.Set;

import com.gdn.common.web.base.BaseResponse;

public class CandidateDTOResponse extends BaseResponse {

  private CandidateDetailDTOResponse candidateDetail;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private String phoneNumber;
<<<<<<< HEAD
  private boolean markForDelete;
  // private Set<CandidatePositionDTOResponse> candidatePositions;
=======
  private Set<CandidatePositionDTOResponse> candidatePositions;
>>>>>>> refs/remotes/bliblidotcom/develop

  public CandidateDetailDTOResponse getCandidateDetail() {
    return candidateDetail;
  }
<<<<<<< HEAD

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

  public boolean isMarkForDelete() {
    return markForDelete;
=======

  public String getEmailAddress() {
    return emailAddress;
  }

  public String getFirstname() {
    return firstName;
  }

  public String getLastname() {
    return lastName;
  }

  public String getPhonenumber() {
    return phoneNumber;
>>>>>>> refs/remotes/bliblidotcom/develop
  }

  public void setCandidateDetail(CandidateDetailDTOResponse candidateDetail) {
    this.candidateDetail = candidateDetail;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

<<<<<<< HEAD
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setMarkForDelete(boolean markForDelete) {
    this.markForDelete = markForDelete;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

=======
  public void setFirstname(String firstName) {
    this.firstName = firstName;
  }

  public void setLastname(String lastName) {
    this.lastName = lastName;
  }

  public void setPhonenumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

>>>>>>> refs/remotes/bliblidotcom/develop
}
