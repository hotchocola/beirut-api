package com.gdn.x.beirut.solr.model;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.gdn.common.base.entity.GdnBaseSolrEntity;

@SolrDocument(solrCoreName = "beirut")
public class Candidate extends GdnBaseSolrEntity {
  public static class Builder {
    private final Candidate build;

    public Builder(String id) {
      build = new Candidate();
    }

    public Candidate build() {
      return build;
    }

    public Builder setUp(String firstName, String lastName, String phoneNumber, String emailAddress,
        List<String> positions, List<String> statuses) {
      build.firstName = firstName;
      build.lastName = lastName;
      build.phoneNumber = phoneNumber;
      build.emailAddress = emailAddress;
      build.positions = positions;
      build.statuses = statuses;
      return this;
    }
  }

  private static final long serialVersionUID = 1L;

  @Field
  private String firstName;

  @Field
  private String lastName;

  @Field
  private String phoneNumber;

  @Field
  private String emailAddress;

  @Field
  private List<String> positions;

  @Field
  private List<String> statuses;

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

  public List<String> getPositions() {
    return positions;
  }

  public List<String> getStatuses() {
    return statuses;
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

  public void setPositions(List<String> positions) {
    this.positions = positions;
  }

  public void setStatuses(List<String> statuses) {
    this.statuses = statuses;
  }

  @Override
  public String toString() {
    return "Candidate [firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
        + phoneNumber + ", emailAddress=" + emailAddress + ", positions=" + positions
        + ", statuses=" + statuses + "]";
  }
}
