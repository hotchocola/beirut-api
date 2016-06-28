package com.gdn.x.beirut.clientsdk;

import com.gdn.common.client.GdnRestClientConfiguration;

public class BeirutApiClientIT {

  private static final String CONTEXT_PATH = "beirut/docs/api/";
  private static final String STORE_ID = "20002";
  private static final String CHANNEL_ID = "web";
  private static final String CLIENT_ID = "XBEIRUT";
  private static final Integer PORT = 8080;
  private static final String HOST = "127.0.0.1";
  private static final String PASSWORD = "DUMMY";
  private static final String USERNAME = "I_TEST_USER";
  private final BeirutApiClient beirutApiClient;

  public BeirutApiClientIT() {
    beirutApiClient = new BeirutApiClient(new GdnRestClientConfiguration(USERNAME, PASSWORD, HOST,
        PORT, CLIENT_ID, CHANNEL_ID, STORE_ID), CONTEXT_PATH);
  }

}
