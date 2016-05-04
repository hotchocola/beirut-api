package com.gdn.x.beirut.dto.response;

import java.util.Set;

import com.gdn.common.web.base.BaseResponse;

public class CandidateDTOResponse extends BaseResponse {

  private CandidateDetailDTOResponse candidatedetail;
  private String emailaddress;
  private String firstname;
  private String lastname;
  private String phonenumber;
  private Set<CandidatePositionDTOResponse> candidatePositions;

  public CandidateDetailDTOResponse getCandidatedetail() {
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

  public void setCandidatedetail(CandidateDetailDTOResponse candidatedetail) {
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
