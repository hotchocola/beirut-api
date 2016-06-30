package com.gdn.x.beirut.clientsdk;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.x.beirut.dto.request.ApplyNewPositionModelDTORequest;
import com.gdn.x.beirut.dto.request.CandidateDetailDTORequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.request.StatusDTORequest;
import com.gdn.x.beirut.dto.request.UpdateCandidateStatusModelDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponseWithoutDetail;
import com.gdn.x.beirut.dto.response.PositionDTOResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfigClientSDK.class})
public class BeirutApiClientIT {

  private static final String CONTEXT_PATH = "/beirut/api";
  private static final String STORE_ID = "20002";
  private static final String CHANNEL_ID = "web";
  private static final String CLIENT_ID = "XBEIRUT";
  private static final Integer PORT = 8180;
  private static final String HOST = "localhost";
  private static final String PASSWORD = "DUMMY";
  private static final String USERNAME = "I_TEST_USER";
  private static final String REQUEST_ID = "ClientAPITest";


  private long timestamp;
  private BeirutApiClient beirutApiClient;
  private List<String> positionIds;
  private List<String> positionIds1;
  private GdnRestListResponse<PositionDTOResponse> resultPosition;
  private GdnRestListResponse<CandidateDTOResponseWithoutDetail> resultCandidate;

  @Before
  public void inititalize() throws Exception {
    beirutApiClient = new BeirutApiClient(new GdnRestClientConfiguration(USERNAME, PASSWORD, HOST,
        PORT, CLIENT_ID, CHANNEL_ID, STORE_ID), CONTEXT_PATH);

    timestamp = System.currentTimeMillis();
    // Generate Position
    List<PositionDTORequest> positionDTORequests = new ArrayList<PositionDTORequest>();
    for (int i = 0; i < 5; i++) {
      PositionDTORequest positionDTORequest = new PositionDTORequest();
      positionDTORequest.setTitle("Software Developer Division " + i + " " + timestamp);
      positionDTORequests.add(positionDTORequest);
    }

    // insert Position
    for (PositionDTORequest positionDTORequest : positionDTORequests) {
      beirutApiClient.insertNewPosition(REQUEST_ID, USERNAME, positionDTORequest);
    }

    // get Position ids
    positionIds = new ArrayList<String>();
    positionIds1 = new ArrayList<String>();
    resultPosition = beirutApiClient.getAllPositionByStoreId(REQUEST_ID, USERNAME);
    for (PositionDTOResponse positionDTOResponse : resultPosition.getContent()) {
      if (positionDTOResponse.getTitle().equals("Software Developer Division 1 " + timestamp)
          || positionDTOResponse.getTitle().equals("Software Developer Division 4 " + timestamp)) {
        positionIds.add(positionDTOResponse.getId());
      }
      if (positionDTOResponse.getTitle().equals("Software Developer Division 2 " + timestamp)
          || positionDTOResponse.getTitle().equals("Software Developer Division 3 " + timestamp)) {
        positionIds1.add(positionDTOResponse.getId());
      }
    }

    // assign positionIds to the new candidate
    String candidateDTORequestString = "{\"emailAddress\": \"asda@egamail.com" + timestamp
        + "\",\"firstName\": \"asducup\",\"lastName\": \"sanusias\",\"phoneNumber\": \"1\",\"positionIds\": [";
    for (String string : positionIds) {
      candidateDTORequestString += "\"" + string + "\",";
    }
    candidateDTORequestString =
        candidateDTORequestString.substring(0, candidateDTORequestString.length() - 1);
    candidateDTORequestString += "]}";

    CandidateDetailDTORequest candidateDetailDTORequestDummy = new CandidateDetailDTORequest();
    candidateDetailDTORequestDummy.setContent(new byte[] {67, 68, 69});

    // inserting new candidate1
    beirutApiClient.insertNewCandidate(REQUEST_ID, USERNAME, candidateDTORequestString,
        candidateDetailDTORequestDummy);
    // assign positionIds1 to the new candidate1
    String candidateDTORequestString1 = "{\"emailAddress\": \"asda@egamail.com1" + timestamp
        + "\",\"firstName\": \"asducup\",\"lastName\": \"sanusias\",\"phoneNumber\": \"11\",\"positionIds\": [";
    for (String string : positionIds1) {
      candidateDTORequestString1 += "\"" + string + "\",";
    }
    candidateDTORequestString1 =
        candidateDTORequestString1.substring(0, candidateDTORequestString1.length() - 1);
    candidateDTORequestString1 += "]}";

    CandidateDetailDTORequest candidateDetailDTORequestDummy1 = new CandidateDetailDTORequest();
    candidateDetailDTORequestDummy1.setContent(new byte[] {98, 99, 100});

    // inserting new candidate1
    beirutApiClient.insertNewCandidate(REQUEST_ID, USERNAME, candidateDTORequestString1,
        candidateDetailDTORequestDummy1);

    // get candidate
    resultCandidate =
        beirutApiClient.getAllCandidateByStoreIdWithPageable(REQUEST_ID, USERNAME, 0, 3);


  }

  // UPDATED IZAL DONE
  @Test
  @Ignore
  public void testApplyNewPosition() throws Exception {
    String idCandidate = resultCandidate.getContent().get(0).getId();
    // ListStringRequest listPositionIdStrings = new ListStringRequest();
    List<String> listString = new ArrayList<String>();
    for (PositionDTOResponse positionDTOResponse : resultPosition.getContent()) {
      if (positionDTOResponse.getTitle().equals("Software Developer Division 5 " + timestamp)) {
        listString.add(positionDTOResponse.getId());
      }
    }
    // listPositionIdStrings.setValues(listString);

    ApplyNewPositionModelDTORequest applyNewPositionModelDTORequest =
        new ApplyNewPositionModelDTORequest();
    applyNewPositionModelDTORequest.setIdCandidate(idCandidate);
    applyNewPositionModelDTORequest.setListPositionIds(listString);

    beirutApiClient.applyNewPosition(REQUEST_ID, USERNAME, applyNewPositionModelDTORequest);
  }

  // UPDATED IZAL DONE
  @Test
  @Ignore
  public void testUpdateCandidatesStatus() throws Exception {

    String idPosition = "";
    for (PositionDTOResponse positionDTOResponse : resultPosition.getContent()) {
      if (positionDTOResponse.getTitle().equals("Software Developer Division 1 " + timestamp)) {
        idPosition = positionDTOResponse.getId();
        break;
      }
    }
    StatusDTORequest status = StatusDTORequest.MEDICAL;
    // ListStringRequest listCandidateIdStrings = new ListStringRequest();
    List<String> listString = new ArrayList<String>();
    System.out.println("SIZE RESULTCANDIDATENYA WOI : " + resultCandidate.getContent().size());
    for (CandidateDTOResponseWithoutDetail candidateDTOResponseWithoutDetail : resultCandidate
        .getContent()) {
      if (candidateDTOResponseWithoutDetail.getEmailAddress()
          .equals("asda@egamail.com1" + timestamp)) {
        listString.add(candidateDTOResponseWithoutDetail.getId());
      }
    }
    // listCandidateIdStrings.setValues(listString);

    UpdateCandidateStatusModelDTORequest updateCandidateStatusModelDTORequest =
        new UpdateCandidateStatusModelDTORequest();
    updateCandidateStatusModelDTORequest.setIdPosition(idPosition);
    updateCandidateStatusModelDTORequest.setStatusDTORequest(status.name());
    updateCandidateStatusModelDTORequest.setIdCandidates(listString);

    // System.out.println("ini idCandidatenya : " + idCandidate);
    beirutApiClient.updateCandidatesStatus(REQUEST_ID, USERNAME,
        updateCandidateStatusModelDTORequest);
  }

  @Test
  @Ignore
  public void testUpdatePosition() throws Exception {
    PositionDTORequest newPosition = new PositionDTORequest();
    newPosition.setTitle("New Title" + timestamp);
    beirutApiClient.updatePosition(REQUEST_ID, USERNAME, positionIds.get(0), newPosition);
    GdnRestListResponse<PositionDTOResponse> result =
        beirutApiClient.getPositionByTitle(REQUEST_ID, USERNAME, "New Title" + timestamp);
    Assert.assertTrue(result.getContent().size() == 1);
  }
}
