package com.gdn.x.beirut.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class CandidateDTOResponse extends BaseResponse{
  public String EMAIL_ADDRESS;

  public String FIRST_NAME;

  public String LAST_NAME;

  public String PHONE_NUMBER;

  public String getEMAIL_ADDRESS() {
    return EMAIL_ADDRESS;
  }

  public String getFIRST_NAME() {
    return FIRST_NAME;
  }

  public String getLAST_NAME() {
    return LAST_NAME;
  }

  public String getPHONE_NUMBER() {
    return PHONE_NUMBER;
  }

  public void setEMAIL_ADDRESS(String eMAIL_ADDRESS) {
    EMAIL_ADDRESS = eMAIL_ADDRESS;
  }

  public void setFIRST_NAME(String fIRST_NAME) {
    FIRST_NAME = fIRST_NAME;
  }

  public void setLAST_NAME(String lAST_NAME) {
    LAST_NAME = lAST_NAME;
  }

  public void setPHONE_NUMBER(String pHONE_NUMBER) {
    PHONE_NUMBER = pHONE_NUMBER;
  }
}
