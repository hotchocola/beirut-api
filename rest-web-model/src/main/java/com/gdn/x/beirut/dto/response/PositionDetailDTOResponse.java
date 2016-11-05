package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class PositionDetailDTOResponse extends BaseResponse {

  private static final long serialVersionUID = 3862586103531052338L;
  private String idPosition;
  private String positionTitle;
  private String jobType;
  private String jobDivision;
  private String jobStatus;
  private String candidateFirstName;
  private String candidateLastName;
  private String currentStatus;

  public String getCandidateFirstName() {
    return candidateFirstName;
  }

  public String getCandidateLastName() {
    return candidateLastName;
  }

  public String getCurrentStatus() {
    return currentStatus;
  }

  public String getIdPosition() {
    return idPosition;
  }

  public String getJobDivision() {
    return jobDivision;
  }

  public String getJobStatus() {
    return jobStatus;
  }

  public String getJobType() {
    return jobType;
  }

  public String getPositionTitle() {
    return positionTitle;
  }

  public void setCandidateFirstName(String candidateFirstName) {
    this.candidateFirstName = candidateFirstName;
  }

  public void setCandidateLastName(String candidateLastName) {
    this.candidateLastName = candidateLastName;
  }

  public void setCurrentStatus(String currentStatus) {
    this.currentStatus = currentStatus;
  }

  public void setIdPosition(String idPosition) {
    this.idPosition = idPosition;
  }

  public void setJobDivision(String jobDivision) {
    this.jobDivision = jobDivision;
  }

  public void setJobStatus(String jobStatus) {
    this.jobStatus = jobStatus;
  }

  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  public void setPositionTitle(String positionTitle) {
    this.positionTitle = positionTitle;
  }

}
