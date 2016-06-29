package com.gdn.x.beirut.clientsdk;

import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URI;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.gdn.client_sdk.shade.org.apache.http.client.methods.HttpPost;
import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.util.GdnHttpClientHelper;
import com.gdn.common.web.param.MandatoryRequestParam;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.x.beirut.dto.request.ListStringRequest;



public class BeirutApiClientTest {
  private static final String requestId = "REQUEST_ID";
  private static final String username = "USERNAME";
  private static final String clientId = "CLIENT_ID";
  private static final String channelId = "CHANNEL_ID";
  private static final String storeId = "STORE_ID";
  private static final String authenticator = "AUTHENTICATOR";

  private static final String HOST = "host";
  private static final Integer PORT = 8080;

  private static final int CONNECTION_TIMEOUT_IN_MS = 6000;
  private static final String CONTEXT_PATH = "/beirut/api";
  private MandatoryRequestParam mandatoryRequestParam;
  private BeirutApiPath beirutApiPath;

  private GdnBaseRestResponse gdnBaseResponse;
  private HashMap<String, String> additionalRequestParam;
  private final String idCandidate = "id_candidate";
  private final ListStringRequest listPositionIdString = new ListStringRequest();


  private HttpPost configRequest;

  @Mock
  private GdnRestClientConfiguration clientConfig;

  @Mock
  private GdnHttpClientHelper httpClientHelper;

  @InjectMocks
  private BeirutApiClient beirutApiClient;

  @Before
  public void initialize() {
    initMocks(this);
    // beirutApiClient = new BeirutApiClient(clientConfig, CONTEXT_PATH);
    this.additionalRequestParam = new HashMap<>();
    this.gdnBaseResponse = new GdnBaseRestResponse(requestId);
  }

  @Test
  public void testApplyNewPosition() throws Exception {
    this.mandatoryRequestParam = MandatoryRequestParam.generateMandatoryRequestParam(storeId,
        channelId, clientId, requestId, username, authenticator);
    this.additionalRequestParam.put("idCandidate", idCandidate);
    this.additionalRequestParam.put("listPositionIdString", String.valueOf(listPositionIdString));
    URI applyNewPosition = new URI("uri");
    Mockito
        .when(
            this.httpClientHelper.getURI(HOST, PORT, CONTEXT_PATH + "/candidate/applyNewPosition/",
                this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(applyNewPosition);

    Mockito.when(this.httpClientHelper.invokePost(applyNewPosition, configRequest, HttpPost.class,
        CONNECTION_TIMEOUT_IN_MS)).thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response = this.beirutApiClient.applyNewPosition(requestId, username,
        idCandidate, listPositionIdString);
    Mockito.verify(
        this.httpClientHelper.getURI(HOST, PORT, CONTEXT_PATH + BeirutApiPath.APPLY_NEW_POSITION,
            this.mandatoryRequestParam, this.additionalRequestParam));
    Mockito.verify(this.httpClientHelper.invokePost(applyNewPosition, configRequest, HttpPost.class,
        CONNECTION_TIMEOUT_IN_MS));
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }
}
