package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class CandidateDTORequest extends BaseRequest {

  private CandidateDetailDTORequest candidatedetail;
  private String emailaddress;
  private String firstname;

  private String lastname;
  private String phonenumber;

  public CandidateDetailDTORequest getCandidatedetail() {
    return candidatedetail;
  }

  public String getEmailaddress() {
    return emailaddress;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public void setCandidatedetail(CandidateDetailDTORequest candidatedetail) {
    this.candidatedetail = candidatedetail;
  }

  public void setEmailaddress(String emailaddress) {
    this.emailaddress = emailaddress;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }
}
