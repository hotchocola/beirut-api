
package com.gdn.x.beirut.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = "Candidate")
public class Candidate extends GdnBaseEntity {

  public static final String EMAIL_ADDRESS = "emailaddress";

  public static final String FIRST_NAME = "firstname";

  public static final String LAST_NAME = "lastname";

  public static final String PHONE_NUMBER = "phonenumber";

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "candidate")
  private CandidateDetail candidatedetail;

  @Column(name = Candidate.EMAIL_ADDRESS)
  private String emailaddress;

  @Column(name = Candidate.FIRST_NAME)
  private String firstname;

  @Column(name = Candidate.LAST_NAME)
  private String lastname;

  @Column(name = Candidate.PHONE_NUMBER)
  private String phonenumber;

  public Candidate(String STORE_ID) {
    super();
    this.setStoreId(STORE_ID);
  }

  public CandidateDetail getCandidatedetail() {
    return this.candidatedetail;
  }

  public String getEmailaddress() {
    return this.emailaddress;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public String getPhonenumber() {
    return this.phonenumber;
  }

  public void setCandidatedetail(CandidateDetail candidatedetail) {
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
