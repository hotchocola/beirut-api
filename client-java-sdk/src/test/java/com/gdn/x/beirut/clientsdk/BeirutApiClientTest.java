package com.gdn.x.beirut.clientsdk;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BeirutApiClientTest {
  private static final String requestId = "REQUEST_ID";
  private static final String username = "USERNAME";


  @Autowired
  private BeirutApiClient beirutApiClient;

  @Before
  public void initialize() {

  }

  @Test
  public void testApplyNewPosition() {
    this.beirutApiClient.applyNewPosition(requestId, username, idCandidate, listPositionIdStrings)
  }
}
