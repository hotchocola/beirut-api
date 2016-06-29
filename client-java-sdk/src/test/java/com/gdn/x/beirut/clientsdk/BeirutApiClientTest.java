package com.gdn.x.beirut.clientsdk;


import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import com.fasterxml.jackson.core.type.TypeReference;
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

  private static final String HOST = "localhost";
  private static final Integer PORT = 8080;

  private static final int CONNECTION_TIMEOUT_IN_MS = 6000;
  private static final String CONTEXT_PATH = "beirut/docs/api";
  private static final String CONTEXT_PATH_TEST = "/beirut/docs/api";
  private MandatoryRequestParam mandatoryRequestParam;

  private GdnBaseRestResponse gdnBaseResponse;
  private HashMap<String, String> additionalRequestParam;
  private final String idCandidate = "id_candidate";
  private final ListStringRequest listPositionIdString = new ListStringRequest();
  private TypeReference<GdnBaseRestResponse> a;
  private final String Json = MediaType.APPLICATION_JSON_VALUE;

  @Mock
  private GdnHttpClientHelper httpClientHelper;

  @Mock
  private GdnRestClientConfiguration clientConfig;

  @InjectMocks
  private BeirutApiClient beirutApiClient;


  @Before
  public void initialize() {
    initMocks(this);
    // <<<<<<< HEAD
    // this.beirutApiClient.setContextPath("beirut/api");
    // when(this.clientConfig.getStoreId()).thenReturn(STORE_ID);
    // when(this.clientConfig.getClientId()).thenReturn(CLIENT_ID);
    // when(this.clientConfig.getHost()).thenReturn(HOST);
    // when(this.clientConfig.getConnectionTimeoutInMs()).thenReturn(CONNECTION_TIMEOUT_IN_MS);
    // when(this.clientConfig.getChannelId()).thenReturn(CHANNEL_ID);
    // when(this.clientConfig.getPort()).thenReturn(PORT);
    // when(this.clientConfig.getUsername()).thenReturn(USERNAME);
    // when(this.clientConfig.getPassword()).thenReturn(PASSWORD);
    // when(this.clientConfig.getMaxConnPerRoute()).thenReturn(MAX_CONN_PER_ROUTE);
    // when(this.clientConfig.getMaxConnTotal()).thenReturn(MAX_CONN_TOTAL);
    // =======
    this.additionalRequestParam = new HashMap<String, String>();
    this.gdnBaseResponse = new GdnBaseRestResponse(requestId);
    List<String> li = new ArrayList<String>();
    li.add("ad");
    this.listPositionIdString.setValues(li);
    this.clientConfig =
        new GdnRestClientConfiguration(username, "dsjh", HOST, PORT, clientId, channelId, storeId);
    this.clientConfig.setConnectionTimeoutInMs(CONNECTION_TIMEOUT_IN_MS);
    this.beirutApiClient = new BeirutApiClient(this.clientConfig, CONTEXT_PATH);

    a = new TypeReference<GdnBaseRestResponse>() {};
    this.beirutApiClient.setTypeRef(a);
    ReflectionTestUtils.setField(beirutApiClient, "httpClientHelper", httpClientHelper,
        GdnHttpClientHelper.class);
    // >>>>>>> eed1ab1993304e9749b107cb67d980bbeee671c3
  }

  @After
  public void noMoreTransaction() {

    Mockito.verifyNoMoreInteractions(this.httpClientHelper);
  }

  @Test
  public void testApplyNewPosition() throws Exception {

    this.mandatoryRequestParam = MandatoryRequestParam.generateMandatoryRequestParam(storeId,
        channelId, clientId, requestId, username, username);
    this.additionalRequestParam.put("idCandidate", idCandidate);
    URI uriApplyNewPosition = new URI("urijh");
    Mockito.when(this.httpClientHelper.getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.APPLY_NEW_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam)).thenReturn(uriApplyNewPosition);
    // System.out.println("getURI(" + " " + HOST + "," + PORT + "," + CONTEXT_PATH_TEST
    // + BeirutApiPath.APPLY_NEW_POSITION + "," + this.mandatoryRequestParam + ","
    // + this.additionalRequestParam); DEBUG
    Mockito
        .when(this.httpClientHelper.invokePostType(uriApplyNewPosition, this.listPositionIdString,
            ListStringRequest.class, a, Json, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response = this.beirutApiClient.applyNewPosition(requestId, username,
        idCandidate, listPositionIdString);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.APPLY_NEW_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriApplyNewPosition,
        this.listPositionIdString, ListStringRequest.class, a, Json, CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

  // @Test
  // public void testUpdatePosition() throws Exception {
  // PositionDTORequest positionDTORequest = new PositionDTORequest();
  // positionDTORequest.setTitle(TITLE);
  // GdnBaseRestResponse gdnBaseRestUpdatePosition = new GdnBaseRestResponse(requestId);
  //
  // String path = "/position/updatePosition";
  // Map<String, String> map = new HashMap<String, String>();
  // map.put("id", ID);
  //
  // URIBuilder builder = new URIBuilder().setScheme("http")
  // .setHost(HOST.replace("http" + "://", "")).setPath(CONTEXT_PATH + path).setPort(PORT)
  // .addParameter(GdnMandatoryRequestParameterUtil.STORE_ID_KEY_PARAMETER, storeId)
  // .addParameter(GdnMandatoryRequestParameterUtil.CHANNEL_ID_KEY_PARAMETER, channelId)
  // .addParameter(GdnMandatoryRequestParameterUtil.REQUEST_ID_KEY_PARAMETER, requestId)
  // .addParameter(GdnMandatoryRequestParameterUtil.CLIENT_ID_KEY_PARAMETER, clientId)
  // .addParameter(GdnMandatoryRequestParameterUtil.USERNAME_KEY_PARAMETER, username)
  // .addParameter(GdnMandatoryRequestParameterUtil.AUTHENTICATOR_KEY, authenticator)
  // .addParameter("id", ID);
  // URI uri = builder.build();
  // System.out.println(uri.toString());
  //
  // Mockito
  // .when(
  // this.httpClientHelper.getURI(HOST,
  // PORT, CONTEXT_PATH + path, MandatoryRequestParam.generateMandatoryRequestParam(
  // storeId, channelId, clientId, requestId, username, username),
  // map))
  // .thenReturn(uri);
  // PositionDTORequest r = new PositionDTORequest();
  // r.setTitle("title");
  // Mockito.when(this.httpClientHelper.invokePost(uri, positionDTORequest,
  // PositionDTOResponse.class, CONNECTION_TIMEOUT_IN_MS)).thenReturn(gdnBaseRestUpdatePosition);
  // this.beirutApiClient.updatePosition(requestId, username, ID, r);
  //
  // }
}
