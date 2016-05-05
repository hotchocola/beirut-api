package com.gdn.x.beirut.dto.response;

import java.util.Set;

import com.gdn.common.web.base.BaseResponse;

public class CandidateDTOResponse extends BaseResponse {

  private CandidateDetailDTOResponse candidateDetail;
  private String emailAddress;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private Set<CandidatePositionDTOResponse> candidatePositions;

  public CandidateDetailDTOResponse getCandidateDetail() {
    return candidateDetail;
  }

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
  }

  public void setCandidateDetail(CandidateDetailDTOResponse candidateDetail) {
    this.candidateDetail = candidateDetail;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public void setFirstname(String firstName) {
    this.firstName = firstName;
  }

  public void setLastname(String lastName) {
    this.lastName = lastName;
  }

  public void setPhonenumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
