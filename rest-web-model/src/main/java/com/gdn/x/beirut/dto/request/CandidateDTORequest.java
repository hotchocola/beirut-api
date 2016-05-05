package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class CandidateDTORequest extends BaseRequest {

  private CandidateDetailDTORequest candidateDetail;
  private String emailAddress;
  private String firstName;

  private String lastName;
  private String phoneNumber;

  public CandidateDetailDTORequest getCandidatedetail() {
    return candidateDetail;
  }

  public String getEmailaddress() {
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

  public void setCandidatedetail(CandidateDetailDTORequest candidatedetail) {
    this.candidateDetail = candidatedetail;
  }

  public void setEmailaddress(String emailaddress) {
    this.emailAddress = emailaddress;
  }

  public void setFirstname(String firstname) {
    this.firstName = firstname;
  }

  public void setLastname(String lastname) {
    this.lastName = lastname;
  }

  public void setPhonenumber(String phonenumber) {
    this.phoneNumber = phonenumber;
  }
}
