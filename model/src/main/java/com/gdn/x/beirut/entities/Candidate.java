
package com.gdn.x.beirut.entities;

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
@Table(name = "Candidate")
public class Candidate extends GdnBaseEntity {

  public static final String EMAIL_ADDRESS = "emailaddress";

  public static final String FIRST_NAME = "firstname";

  public static final String LAST_NAME = "lastname";

  public static final String PHONE_NUMBER = "phonenumber";

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidate")
  private CandidateDetail candidatedetail;

  @Column(name = Candidate.EMAIL_ADDRESS)
  private String emailaddress;

  @Column(name = Candidate.FIRST_NAME)
  private String firstname;

  @Column(name = Candidate.LAST_NAME)
  private String lastname;

  @Column(name = Candidate.PHONE_NUMBER)
  private String phonenumber;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "candidate")
  private Set<CandidatePosition> candidatePositions;

  public Candidate(String STORE_ID) {
    super();
    this.setStoreId(STORE_ID);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    final Candidate other = (Candidate) obj;
    if (candidatedetail == null) {
      if (other.candidatedetail != null)
        return false;
    } else if (!candidatedetail.equals(other.candidatedetail))
      return false;
    if (emailaddress == null) {
      if (other.emailaddress != null)
        return false;
    } else if (!emailaddress.equals(other.emailaddress))
      return false;
    if (firstname == null) {
      if (other.firstname != null)
        return false;
    } else if (!firstname.equals(other.firstname))
      return false;
    if (lastname == null) {
      if (other.lastname != null)
        return false;
    } else if (!lastname.equals(other.lastname))
      return false;
    if (phonenumber == null) {
      if (other.phonenumber != null)
        return false;
    } else if (!phonenumber.equals(other.phonenumber))
      return false;
    return true;
  }

  public Set<CandidatePosition> getCandidatePositions() {
    return candidatePositions;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((candidatedetail == null) ? 0 : candidatedetail.hashCode());
    result = prime * result + ((emailaddress == null) ? 0 : emailaddress.hashCode());
    result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
    result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
    result = prime * result + ((phonenumber == null) ? 0 : phonenumber.hashCode());
    return result;
  }

  public void setCandidatedetail(CandidateDetail candidatedetail) {
    this.candidatedetail = candidatedetail;
  }

  public void setCandidatePositions(Set<CandidatePosition> candidatePositions) {
    this.candidatePositions = candidatePositions;
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
