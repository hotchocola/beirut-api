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
import com.gdn.common.web.wrapper.request.SimpleRequestHolder;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;



public class BeirutApiClientTest {

  private static final String REQUEST_ID = "REQUEST_ID";
  private static final String USERNAME = "USERNAME";
  private static final String CLIENT_ID = "CLIENT_ID";
  private static final String CHANNEL_ID = "CHANNEL_ID";
  private static final String STORE_ID = "STORE_ID";
  private static final String HOST = "localhost";
  private static final String PASSWORD = "password";
  private static final String CONTEXT_PATH = "beirut/docs/api";
  private static final String CONTEXT_PATH_TEST = "/beirut/docs/api";
  private static final String ID_CANDIDATE = "id_candidate";
  private static final String TITLE = "myTitle";
  private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
  private static final Integer PORT = 8080;
  private static final int PAGE = 0;
  private static final int SIZE = 5;
  private static final int CONNECTION_TIMEOUT_IN_MS = 6000;
  private static final String ID = "id";

  private MandatoryRequestParam mandatoryRequestParam;
  private GdnBaseRestResponse gdnBaseResponse;
  private GdnRestListResponse<CandidateDTOResponse> gdnRestListCandidate;
  private SimpleRequestHolder simpleRequestHolder;
  private HashMap<String, String> additionalRequestParam;
  private TypeReference<GdnBaseRestResponse> typeRef;
  private final ListStringRequest listPositionIdString = new ListStringRequest();
  private final Long start = new Long(0);
  private final Long end = new Long(99999999);

  @Mock
  private GdnHttpClientHelper httpClientHelper;

  @Mock
  private GdnRestClientConfiguration clientConfig;

  @InjectMocks
  private BeirutApiClient beirutApiClient;


  @Before
  public void initialize() throws Exception {
    initMocks(this);
    this.gdnBaseResponse = new GdnBaseRestResponse(REQUEST_ID);
    List<String> list = new ArrayList<String>();
    list.add("ad");
    this.listPositionIdString.setValues(list);
    this.clientConfig = new GdnRestClientConfiguration(USERNAME, PASSWORD, HOST, PORT, CLIENT_ID,
        CHANNEL_ID, STORE_ID);
    this.clientConfig.setConnectionTimeoutInMs(CONNECTION_TIMEOUT_IN_MS);
    this.beirutApiClient = new BeirutApiClient(this.clientConfig, CONTEXT_PATH);
    this.mandatoryRequestParam = MandatoryRequestParam.generateMandatoryRequestParam(STORE_ID,
        CHANNEL_ID, CLIENT_ID, REQUEST_ID, USERNAME, USERNAME);
    typeRef = new TypeReference<GdnBaseRestResponse>() {};
    this.beirutApiClient.setTypeRef(typeRef);
    ReflectionTestUtils.setField(beirutApiClient, "httpClientHelper", httpClientHelper,
        GdnHttpClientHelper.class);
  }

  @After
  public void noMoreTransaction() {
    Mockito.verifyNoMoreInteractions(this.httpClientHelper);
  }

  @Test
  public void testApplyNewPosition() throws Exception {

    this.additionalRequestParam = new HashMap<String, String>();
    this.additionalRequestParam.put("idCandidate", ID_CANDIDATE);
    URI uriApplyNewPosition = new URI("/candidate/applyNewPosition");
    Mockito.when(this.httpClientHelper.getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.APPLY_NEW_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam)).thenReturn(uriApplyNewPosition);
    // System.out.println("getURI(" + " " + HOST + "," + PORT + "," + CONTEXT_PATH_TEST
    // + BeirutApiPath.APPLY_NEW_POSITION + "," + this.mandatoryRequestParam + ","
    // + this.additionalRequestParam); DEBUG
    Mockito
        .when(this.httpClientHelper.invokePostType(uriApplyNewPosition, this.listPositionIdString,

            ListStringRequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response = this.beirutApiClient.applyNewPosition(REQUEST_ID, USERNAME,
        ID_CANDIDATE, listPositionIdString);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.APPLY_NEW_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriApplyNewPosition,

        this.listPositionIdString, ListStringRequest.class, typeRef, JSON,
        CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

  @Test
  public void testDeleteCandidate() throws Exception {
    URI uriDeleteCandidate = new URI("/candidate/deleteCandidate");
    Mockito.when(
        this.httpClientHelper.getURI(HOST, PORT, CONTEXT_PATH_TEST + BeirutApiPath.DELETE_CANDIDATE,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriDeleteCandidate);
    Mockito
        .when(this.httpClientHelper.invokePostType(uriDeleteCandidate, this.listPositionIdString,
            ListStringRequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response =
        this.beirutApiClient.deleteCandidate(REQUEST_ID, USERNAME, listPositionIdString);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.DELETE_CANDIDATE, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriDeleteCandidate,
        this.listPositionIdString, ListStringRequest.class, typeRef, JSON,
        CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

  @Test
  public void testDeletePosition() throws Exception {
    URI uriDeletePosition = new URI("/position/deletePosition");
    Mockito.when(
        this.httpClientHelper.getURI(HOST, PORT, CONTEXT_PATH_TEST + BeirutApiPath.DELETE_POSITION,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriDeletePosition);
    Mockito
        .when(this.httpClientHelper.invokePostType(uriDeletePosition, this.listPositionIdString,
            ListStringRequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response =
        this.beirutApiClient.deletePosition(REQUEST_ID, USERNAME, listPositionIdString);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.DELETE_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriDeletePosition,
        this.listPositionIdString, ListStringRequest.class, typeRef, JSON,
        CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

  // GdnBaseRestResponse updateCandidatesStatus(String requestId, String username,
  // StatusDTORequest status, String idPosition, ListStringRequest idCandidates)
  // @Test
  // public void testUpdateCandidateStatus() throws Exception {
  // StatusDTORequest statusDTORequest = StatusDTORequest.HEAD;
  // this.additionalRequestParam = new HashMap<String, String>();
  // this.additionalRequestParam.put("status", "");
  // URI uriUpdateCandidateStatus = new URI("/position/updateCandidateStatus");
  // Mockito.when(this.httpClientHelper.getURI(HOST, PORT,
  // CONTEXT_PATH_TEST + BeirutApiPath.UPDATE_CANDIDATE_STATUS, this.mandatoryRequestParam,
  // this.additionalRequestParam)).thenReturn(uriUpdateCandidateStatus);
  // Mockito.when(
  // this.httpClientHelper.invokePostType(uriUpdateCandidateStatus, this.listPositionIdString,
  // ListStringRequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
  // .thenReturn(this.gdnBaseResponse);
  // GdnBaseRestResponse response = this.beirutApiClient.updateCandidatesStatus(REQUEST_ID,
  // USERNAME,
  // statusDTORequest, ID_CANDIDATE, listPositionIdString);
  // Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
  // CONTEXT_PATH_TEST + BeirutApiPath.UPDATE_CANDIDATE_STATUS, this.mandatoryRequestParam,
  // this.additionalRequestParam);
  // Mockito.verify(this.httpClientHelper).invokePostType(uriUpdateCandidateStatus,
  // this.listPositionIdString, ListStringRequest.class, typeRef, JSON,
  // CONNECTION_TIMEOUT_IN_MS);
  // }

  @Test
  public void testUpdatePosition() throws Exception {
    PositionDTORequest positionDTORequest = new PositionDTORequest();
    positionDTORequest.setTitle(TITLE);
    this.additionalRequestParam = new HashMap<String, String>();
    this.additionalRequestParam.put("id", ID_CANDIDATE);
    URI uriUpdatePosition = new URI("/position/updatePosition");
    Mockito.when(
        this.httpClientHelper.getURI(HOST, PORT, CONTEXT_PATH_TEST + BeirutApiPath.UPDATE_POSITION,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriUpdatePosition);
    Mockito
        .when(this.httpClientHelper.invokePostType(uriUpdatePosition, positionDTORequest,
            PositionDTORequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(gdnBaseResponse);
    GdnBaseRestResponse response =
        this.beirutApiClient.updatePosition(REQUEST_ID, USERNAME, ID_CANDIDATE, positionDTORequest);
    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.UPDATE_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriUpdatePosition, positionDTORequest,
        PositionDTORequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(response);
    Assert.assertEquals(gdnBaseResponse, response);
    // this.beirutApiClient.updatePosition(requestId, username, id, positionDTORequest)
  }

  // @Test
  // public void testFindCandidateByCreatedDateBetweenAndStoreId() throws Exception {
  // URI uriFindCandidateByCreatedDate =
  // new URI("/candidate/findCandidateByCreatedDateBetweenAndStoreId");
  // this.additionalRequestParam = new HashMap<>();
  // this.additionalRequestParam.put("start", String.valueOf(start));
  // this.additionalRequestParam.put("end", String.valueOf(end));
  // this.additionalRequestParam.put("page", String.valueOf(PAGE));
  // this.additionalRequestParam.put("size", String.valueOf(SIZE));
  // Mockito
  // .when(this.httpClientHelper.getURI(HOST, PORT,
  // CONTEXT_PATH_TEST + BeirutApiPath.FIND_CANDIDATE_BY_CREATED_DATE_BETWEEN_AND_STOREID,
  // this.mandatoryRequestParam, this.additionalRequestParam))
  // .thenReturn(uriFindCandidateByCreatedDate);
  // Mockito
  // .when(this.httpClientHelper.invokeGetSummary(uriFindCandidateByCreatedDate,
  // CandidateDTOResponse.class, CONNECTION_TIMEOUT_IN_MS))
  // .thenReturn(this.gdnRestListCandidate);
  //
  // GdnRestListResponse<CandidateDTOResponse> response = this.beirutApiClient
  // .findCandidateByCreatedDateBetweenAndStoreId(REQUEST_ID, USERNAME, start, end, PAGE, SIZE);
  //
  // Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
  // CONTEXT_PATH_TEST + BeirutApiPath.FIND_CANDIDATE_BY_CREATED_DATE_BETWEEN_AND_STOREID,
  // this.mandatoryRequestParam, this.additionalRequestParam);
  // Mockito.verify(this.httpClientHelper).invokeGetSummary(uriFindCandidateByCreatedDate,
  // CandidateDTOResponse.class, CONNECTION_TIMEOUT_IN_MS);
  // Assert.assertNotNull(gdnRestListCandidate);
  // Assert.assertEquals(gdnRestListCandidate, response);
  // }

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
