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
import com.gdn.client_sdk.shade.org.apache.http.client.methods.HttpRequestBase;
import com.gdn.client_sdk.shade.org.apache.http.impl.client.CloseableHttpClient;
import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.util.GdnHttpClientHelper;
import com.gdn.common.web.param.MandatoryRequestParam;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.x.beirut.dto.request.CandidateDetailDTORequest;
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponseWithoutDetail;
import com.gdn.x.beirut.dto.response.CandidatePositionDTOResponse;
import com.gdn.x.beirut.dto.response.CandidatePositionSolrDTOResponse;
import com.gdn.x.beirut.dto.response.PositionDTOResponse;
import com.gdn.x.beirut.dto.response.PositionDetailDTOResponse;



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
  private static final String ID_POSITION = "id_position";
  private static final String QUERY = "query";
  private static final String JSON = MediaType.APPLICATION_JSON_VALUE;
  private static final Integer PORT = 8080;
  private static final int PAGE = 0;
  private static final int SIZE = 5;
  private static final int CONNECTION_TIMEOUT_IN_MS = 6000;
  private static final String ID = "ID";
  private static final Boolean MARK_FOR_DELETE = false;
  private static final String TITLE = "TITLE";

  private MandatoryRequestParam mandatoryRequestParam;
  private GdnBaseRestResponse gdnBaseResponse;
  private CandidateDetailDTORequest candidateDetailDTORequest;
  private PositionDTORequest positionDTORequest;
  private TypeReference<GdnBaseRestResponse> typeRef;

  private GdnRestListResponse<CandidatePositionSolrDTOResponse> gdnRestListCandidatePositionSolrDTOResponse;
  private GdnRestSingleResponse<CandidatePositionDTOResponse> gdnRestSingleCandidatePositionDTOResponse;
  private GdnRestListResponse<PositionDTOResponse> gdnRestListPositionDTOResponse;
  private GdnRestListResponse<CandidateDTOResponseWithoutDetail> gdnRestListCandidateDTOResponseWithoutDetail;
  private GdnRestListResponse<PositionDetailDTOResponse> gdnRestListPositionDetailDTOResponse;

  private HashMap<String, String> additionalRequestParam;
  private final ListStringRequest listPositionIdString = new ListStringRequest();
  private final String candidateDTORequestString = "dtoreqstring";
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
    List<String> list = new ArrayList<>();
    list.add("ad");
    this.listPositionIdString.setValues(list);

    this.gdnRestSingleCandidatePositionDTOResponse =
        new GdnRestSingleResponse<CandidatePositionDTOResponse>();
    this.gdnRestListCandidatePositionSolrDTOResponse =
        new GdnRestListResponse<CandidatePositionSolrDTOResponse>();
    this.gdnRestListPositionDTOResponse = new GdnRestListResponse<PositionDTOResponse>();
    this.gdnRestListCandidateDTOResponseWithoutDetail =
        new GdnRestListResponse<CandidateDTOResponseWithoutDetail>();
    this.gdnRestListPositionDetailDTOResponse =
        new GdnRestListResponse<PositionDetailDTOResponse>();

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
  public void noMoreTransaction() {}

  @Test
  public void testApplyNewPosition() throws Exception {
    this.additionalRequestParam = new HashMap<>();
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

  @Test
  public void testGetAllCandidateByStoreIdAndMarkForDeleteWithPageable() throws Exception {
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("markForDelete", String.valueOf(MARK_FOR_DELETE));
    this.additionalRequestParam.put("page", String.valueOf(PAGE));
    this.additionalRequestParam.put("size", String.valueOf(SIZE));
    URI uriGetAllCandidateByStoreIdAndMarkForDeleteWithPageable =
        new URI("/candidate/getAllCandidatesByStoreIdWithPageable");
    Mockito
        .when(this.httpClientHelper.getURI(HOST, PORT,
            CONTEXT_PATH_TEST
                + BeirutApiPath.GET_ALL_CANDIDATE_BY_STOREID_AND_MARK_FOR_DELETE_WITH_PAGEABLE,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriGetAllCandidateByStoreIdAndMarkForDeleteWithPageable);
    Mockito
        .when(this.httpClientHelper.invokeGetSummary(Mockito.any(HttpRequestBase.class),
            Mockito.eq(CandidateDTOResponseWithoutDetail.class),
            Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class)))
        .thenReturn(gdnRestListCandidateDTOResponseWithoutDetail);

    GdnRestListResponse<CandidateDTOResponseWithoutDetail> response =
        this.beirutApiClient.getAllCandidateByStoreIdAndMarkForDeleteWithPageable(REQUEST_ID,
            USERNAME, MARK_FOR_DELETE, PAGE, SIZE);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST
            + BeirutApiPath.GET_ALL_CANDIDATE_BY_STOREID_AND_MARK_FOR_DELETE_WITH_PAGEABLE,
        this.mandatoryRequestParam, this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(CandidateDTOResponseWithoutDetail.class),
        Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestListCandidateDTOResponseWithoutDetail);
    Assert.assertEquals(gdnRestListCandidateDTOResponseWithoutDetail, response);
  }

  @Test
  public void testGetAllCandidateByStoreIdWithPageable() throws Exception {
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("page", String.valueOf(PAGE));
    this.additionalRequestParam.put("size", String.valueOf(SIZE));
    URI uriGetAllCandidateByStoreIdWithPageable =
        new URI("/candidate/getAllCandidatesByStoreIdWithPageable");
    Mockito
        .when(this.httpClientHelper.getURI(HOST, PORT,
            CONTEXT_PATH_TEST + BeirutApiPath.GET_ALL_CANDIDATE_BY_STORE_ID_WITH_PAGEABLE,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriGetAllCandidateByStoreIdWithPageable);
    Mockito
        .when(this.httpClientHelper.invokeGetSummary(Mockito.any(HttpRequestBase.class),
            Mockito.eq(CandidateDTOResponseWithoutDetail.class),
            Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class)))
        .thenReturn(gdnRestListCandidateDTOResponseWithoutDetail);

    GdnRestListResponse<CandidateDTOResponseWithoutDetail> response =
        this.beirutApiClient.getAllCandidateByStoreIdWithPageable(REQUEST_ID, USERNAME, PAGE, SIZE);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_ALL_CANDIDATE_BY_STORE_ID_WITH_PAGEABLE,
        this.mandatoryRequestParam, this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(CandidateDTOResponseWithoutDetail.class),
        Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestListCandidateDTOResponseWithoutDetail);
    Assert.assertEquals(gdnRestListCandidateDTOResponseWithoutDetail, response);
  }

  @Test
  public void testGetAllPositionByStoreId() throws Exception {
    URI uriGetAllPositionByStoreId = new URI("/position/getAllPosition");
    Mockito
        .when(this.httpClientHelper.getURI(HOST, PORT,
            CONTEXT_PATH_TEST + BeirutApiPath.GET_ALL_POSITION_BY_STOREID,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriGetAllPositionByStoreId);
    Mockito.when(this.httpClientHelper.invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class))).thenReturn(gdnRestListPositionDTOResponse);

    GdnRestListResponse<PositionDTOResponse> response =
        this.beirutApiClient.getAllPositionByStoreId(REQUEST_ID, USERNAME);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_ALL_POSITION_BY_STOREID, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestListPositionDTOResponse);
    Assert.assertEquals(gdnRestListPositionDTOResponse, response);
  }

  @Test
  public void testGetAllPositionWithPageable() throws Exception {
    URI uriGetAllPositionWithPageable = new URI("/position/getAllPositionWithPageable");
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("page", String.valueOf(PAGE));
    this.additionalRequestParam.put("size", String.valueOf(SIZE));
    Mockito
        .when(this.httpClientHelper.getURI(HOST, PORT,
            CONTEXT_PATH_TEST + BeirutApiPath.GET_ALL_POSITION_WITH_PAGEABLE,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriGetAllPositionWithPageable);
    Mockito.when(this.httpClientHelper.invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class))).thenReturn(gdnRestListPositionDTOResponse);

    GdnRestListResponse<PositionDTOResponse> response =
        this.beirutApiClient.getAllPositionWithPageable(REQUEST_ID, USERNAME, PAGE, SIZE);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_ALL_POSITION_WITH_PAGEABLE,
        this.mandatoryRequestParam, this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestListPositionDTOResponse);
    Assert.assertEquals(gdnRestListPositionDTOResponse, response);
  }

  @Test
  public void testGetCandidatePositionBySolrQuery() throws Exception {
    URI uriGetCandidatePositionBySolrQuery = new URI("/candidate/getCandidatePositionBySolrQuery");
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("query", QUERY);
    this.additionalRequestParam.put("page", String.valueOf(PAGE));
    this.additionalRequestParam.put("size", String.valueOf(SIZE));
    Mockito
        .when(this.httpClientHelper.getURI(HOST, PORT,
            CONTEXT_PATH_TEST + BeirutApiPath.GET_CANDIDATE_POSITION_BY_SOLR_QUERY,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriGetCandidatePositionBySolrQuery);
    Mockito
        .when(this.httpClientHelper.invokeGetSummary(Mockito.any(HttpRequestBase.class),
            Mockito.eq(CandidatePositionSolrDTOResponse.class),
            Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class)))
        .thenReturn(gdnRestListCandidatePositionSolrDTOResponse);

    GdnRestListResponse<CandidatePositionSolrDTOResponse> response = this.beirutApiClient
        .getCandidatePositionBySolrQuery(REQUEST_ID, USERNAME, QUERY, PAGE, SIZE);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_CANDIDATE_POSITION_BY_SOLR_QUERY,
        this.mandatoryRequestParam, this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(CandidatePositionSolrDTOResponse.class),
        Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestListCandidatePositionSolrDTOResponse);
    Assert.assertEquals(gdnRestListCandidatePositionSolrDTOResponse, response);
  }

  @Test
  public void testGetCandidatePositionDetailByStoreIdWithLogs() throws Exception {
    URI uriGetCandidatePositionDetailByStoreIdWithLogs =
        new URI("/candidate/getCandidatePositionDetailByStoreIdWithLogs");
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("query", QUERY);
    this.additionalRequestParam.put("idCandidate", String.valueOf(ID_CANDIDATE));
    this.additionalRequestParam.put("idPosition", String.valueOf(ID_POSITION));
    Mockito
        .when(this.httpClientHelper.getURI(HOST, PORT,
            CONTEXT_PATH_TEST + BeirutApiPath.GET_CANDIDATE_POSITION_DETAIL_BY_STOREID_WITH_LOGS,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriGetCandidatePositionDetailByStoreIdWithLogs);
    Mockito
        .when(this.httpClientHelper.invokeGetSingle(Mockito.any(HttpRequestBase.class),
            Mockito.eq(CandidatePositionDTOResponse.class),
            Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class)))
        .thenReturn(gdnRestSingleCandidatePositionDTOResponse);

    GdnRestSingleResponse<CandidatePositionDTOResponse> response =
        this.beirutApiClient.getCandidatePositionDetailByStoreIdWithLogs(REQUEST_ID, USERNAME,
            QUERY, ID_CANDIDATE, ID_POSITION);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_CANDIDATE_POSITION_DETAIL_BY_STOREID_WITH_LOGS,
        this.mandatoryRequestParam, this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSingle(Mockito.any(HttpRequestBase.class),
        Mockito.eq(CandidatePositionDTOResponse.class),
        Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestSingleCandidatePositionDTOResponse);
    Assert.assertEquals(gdnRestSingleCandidatePositionDTOResponse, response);
  }

  @Test
  public void testGetPositionByStoreIdAndMarkForDelete() throws Exception {
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("markForDelete", String.valueOf(MARK_FOR_DELETE));
    URI uriGetPositionByStoreIdAndMarkForDelete =
        new URI("/position/getPositionByStoreIdAndMarkForDelete");
    Mockito
        .when(this.httpClientHelper.getURI(HOST, PORT,
            CONTEXT_PATH_TEST + BeirutApiPath.GET_POSITION_BY_STOREID_AND_MARK_FOR_DELETE,
            this.mandatoryRequestParam, this.additionalRequestParam))
        .thenReturn(uriGetPositionByStoreIdAndMarkForDelete);
    Mockito.when(this.httpClientHelper.invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class))).thenReturn(gdnRestListPositionDTOResponse);

    GdnRestListResponse<PositionDTOResponse> response = this.beirutApiClient
        .getPositionByStoreIdAndMarkForDelete(REQUEST_ID, USERNAME, MARK_FOR_DELETE);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_POSITION_BY_STOREID_AND_MARK_FOR_DELETE,
        this.mandatoryRequestParam, this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestListPositionDTOResponse);
    Assert.assertEquals(gdnRestListPositionDTOResponse, response);
  }

  @Test
  public void testGetPositionByTitle() throws Exception {
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("title", TITLE);
    URI uriGetPositionByTitle = new URI("/position/getPositionByTitle");
    Mockito.when(this.httpClientHelper.getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_POSITION_TITLE, this.mandatoryRequestParam,
        this.additionalRequestParam)).thenReturn(uriGetPositionByTitle);
    Mockito.when(this.httpClientHelper.invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class))).thenReturn(gdnRestListPositionDTOResponse);

    GdnRestListResponse<PositionDTOResponse> response =
        this.beirutApiClient.getPositionByTitle(REQUEST_ID, USERNAME, TITLE);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_POSITION_TITLE, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestListPositionDTOResponse);
    Assert.assertEquals(gdnRestListPositionDTOResponse, response);
  }

  @Test
  public void testGetPositionDetailById() throws Exception {
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("id", ID);
    URI uriInsertNewCandidate = new URI("/position/getPositionDetail");
    Mockito.when(this.httpClientHelper.getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_POSITION_DETAIL_BY_ID, this.mandatoryRequestParam,
        this.additionalRequestParam)).thenReturn(uriInsertNewCandidate);
    Mockito
        .when(this.httpClientHelper.invokeGetSummary(Mockito.any(HttpRequestBase.class),
            Mockito.eq(PositionDetailDTOResponse.class),
            Mockito.eq(MediaType.APPLICATION_JSON_VALUE), Mockito.any(CloseableHttpClient.class)))
        .thenReturn(gdnRestListPositionDetailDTOResponse);

    GdnRestListResponse<PositionDetailDTOResponse> response =
        this.beirutApiClient.getPositionDetailById(REQUEST_ID, USERNAME, ID);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.GET_POSITION_DETAIL_BY_ID, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokeGetSummary(Mockito.any(HttpRequestBase.class),
        Mockito.eq(PositionDetailDTOResponse.class), Mockito.eq(MediaType.APPLICATION_JSON_VALUE),
        Mockito.any(CloseableHttpClient.class));

    Assert.assertNotNull(gdnRestListPositionDetailDTOResponse);
    Assert.assertEquals(gdnRestListPositionDetailDTOResponse, response);
  }

  @Test
  public void testInsertNewCandidate() throws Exception {
    this.additionalRequestParam = new HashMap<>();
    this.additionalRequestParam.put("candidateDTORequestString", candidateDTORequestString);
    URI uriInsertNewCandidate = new URI("/candidate/insertNewCandidate");
    Mockito.when(this.httpClientHelper.getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.INSERT_NEW_CANDIDATE, this.mandatoryRequestParam,
        this.additionalRequestParam)).thenReturn(uriInsertNewCandidate);
    Mockito.when(
        this.httpClientHelper.invokePostType(uriInsertNewCandidate, this.candidateDetailDTORequest,
            CandidateDetailDTORequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response = this.beirutApiClient.insertNewCandidate(REQUEST_ID, USERNAME,
        candidateDTORequestString, candidateDetailDTORequest);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.INSERT_NEW_CANDIDATE, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriInsertNewCandidate,
        this.candidateDetailDTORequest, CandidateDetailDTORequest.class, typeRef, JSON,
        CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

  @Test
  public void testInsertNewPosition() throws Exception {
    URI uriInsertNewPosition = new URI("/position/insertNewPosition");
    Mockito.when(this.httpClientHelper.getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.INSERT_NEW_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam)).thenReturn(uriInsertNewPosition);
    Mockito
        .when(this.httpClientHelper.invokePostType(uriInsertNewPosition, this.positionDTORequest,
            PositionDTORequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS))
        .thenReturn(this.gdnBaseResponse);

    GdnBaseRestResponse response =
        this.beirutApiClient.insertNewPosition(REQUEST_ID, USERNAME, positionDTORequest);

    Mockito.verify(this.httpClientHelper).getURI(HOST, PORT,
        CONTEXT_PATH_TEST + BeirutApiPath.INSERT_NEW_POSITION, this.mandatoryRequestParam,
        this.additionalRequestParam);
    Mockito.verify(this.httpClientHelper).invokePostType(uriInsertNewPosition,
        this.positionDTORequest, PositionDTORequest.class, typeRef, JSON, CONNECTION_TIMEOUT_IN_MS);
    Assert.assertNotNull(gdnBaseResponse);
    Assert.assertEquals(gdnBaseResponse, response);
  }

}
