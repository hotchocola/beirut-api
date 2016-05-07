package com.gdn.x.beirut.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class CandidateDTORequest extends BaseRequest {

  private CandidateDetailDTORequest candidateDetail;
  private String emailAddress;
  private String firstName;
<<<<<<< HEAD
  private String lastName;
  private String phoneNumber;

  public CandidateDetailDTORequest getCandidateDetail() {
    return candidateDetail;
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

  public void setCandidateDetail(CandidateDetailDTORequest candidateDetail) {
    this.candidateDetail = candidateDetail;
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
=======

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
>>>>>>> refs/remotes/bliblidotcom/develop
  }

}
