package com.gdn.x.beirut.client;

import java.net.URI;
import java.util.Map;

import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.web.client.GdnBaseRestCrudClient;

public class BeirutApiClient extends GdnBaseRestCrudClient {

  private static final String JSON_TYPE = "application/json";

  public BeirutApiClient(GdnRestClientConfiguration clientConfig) {
    super(clientConfig);
    // TODO Auto-generated constructor stub
  }

  private URI generateURI(String path, String requestId, String username,
      Map<String, String> additionalParameterMap) throws Exception {
    String location = getContextPath() + path;
    return getHttpClientHelper().getURI(getClientConfig().getHost(), getClientConfig().getPort(),
        location, getMandatoryParameter(requestId, username), additionalParameterMap);
  }


}
