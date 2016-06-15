package com.gdn.x.beirut.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.CandidatesPositionStatusDTOWrapper;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.request.StatusDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;
import com.gdn.x.beirut.services.CandidateService;
import com.gdn.x.beirut.services.PositionService;

public class CandidateControllerTest {
  private static final String STORE_ID = "storeId";
  private static final String CLIENT_ID = "clientId";
  private static final String CHANNEL_ID = "channelId";
  private static final String REQUEST_ID = "requestId";
  private static final String USERNAME = "username";
  private static final String ID = "ID";
  private static final String PHONE_NUMBER = "123";
  private static final String POSITION_ID = "22bf3cdb-d49e-4534-af52-9ce28064e7f3";
  private static final String POSITION_TITLE = "POS_TITLE";
  private ObjectMapper objectMapper;
  private Mapper beanMapper;

  private MockMvc mockMVC;
  private final Candidate candidate = new Candidate();
  private final List<Candidate> candidates = new LinkedList<>();

  private final List<CandidateDTOResponse> candidateResponse = new ArrayList<>();

  @Mock
  private CandidateService candidateService;

  @Mock
  private PositionService positionService;

  @InjectMocks
  private CandidateController candidateController;

  @Before
  public void initialize() throws Exception {
    initMocks(this);
    this.mockMVC = standaloneSetup(this.candidateController).build();
    beanMapper = new DozerBeanMapper();
    objectMapper = new ObjectMapper();
    candidateController.setDozerMapper(beanMapper);
    candidateController.setObjectMapper(objectMapper);
    candidate.setId("ID");
    candidate.setStoreId(STORE_ID);
    candidate.setCandidateDetail(new CandidateDetail());
    candidate.setPhoneNumber("1234567890");
    candidate.setEmailAddress("egaegaega@ega.ega");
    candidate.setCreatedBy("ega");
    candidate.setLastName("Prianto");
    candidate.setFirstName("Ega");
    candidates.add(candidate);

    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, beanMapper);
      candidateResponse.add(newCandidateDTORes);
    }

  }


  @Test
  public void testFindCandidateById() throws Exception {
    String uri = "/api/candidate/findCandidateById";
    Mockito.when(this.candidateService.getCandidate(ID)).thenReturn(candidate);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("id", ID))
        .andExpect(status().isOk());
    GdnRestSingleResponse<CandidateDTOResponse> res = this.candidateController
        .findCandidateById(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME, ID);
    Assert.assertTrue(res.getValue().getId().equals(candidate.getId()));
    Assert.assertTrue(res.getValue().getFirstName().equals(candidate.getFirstName()));
    Assert.assertTrue(res.getValue().getLastName().equals(candidate.getLastName()));
    Assert.assertTrue(res.getValue().getPhoneNumber().equals(candidate.getPhoneNumber()));
    Assert.assertTrue(res.getValue().getId().equals(candidate.getId()));
    Mockito.verify(this.candidateService, Mockito.times(2)).getCandidate(ID);
  }

  @Test
  public void testFindCandidateByPhoneNumber() throws Exception {
    String uri = "/api/candidate/findCandidateByPhoneNumber";
    Mockito.when(this.candidateService.searchCandidateByPhoneNumber(PHONE_NUMBER))
        .thenReturn(candidates);
    this.mockMVC
        .perform(
            MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID).param("storeId", STORE_ID)
                .param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
                .param("username", USERNAME).param("phoneNumber", PHONE_NUMBER))
        .andExpect(status().isOk());
    GdnRestListResponse<CandidateDTOResponse> res =
        this.candidateController.findCandidateByPhoneNumber(CLIENT_ID, STORE_ID, REQUEST_ID,
            CHANNEL_ID, USERNAME, PHONE_NUMBER);
    GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
        candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
    for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchCandidateByPhoneNumber(PHONE_NUMBER);
  }

  @Test
  public void testGetAllCandidate() throws Exception {
    String uri = "/api/candidate/getAllCandidate";
    Mockito.when(this.candidateService.getAllCandidates()).thenReturn(candidates);

    this.mockMVC.perform(MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID)
        .param("storeId", STORE_ID).param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
        .param("username", USERNAME)).andExpect(status().isOk());
    GdnRestListResponse<CandidateDTOResponse> res = this.candidateController
        .getAllCandidate(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME);
    GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
        candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
    for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2)).getAllCandidates();
  }

  @Test
  public void testInsertNewCandidate() throws Exception {
    String uri = "/api/candidate/insertNewCandidate";


    String candidateDTORequestString = FileUtils
        .readFileToString(new File("src/test/resources/JSON/candidateDTORequestString.txt"));
    FileInputStream inputFile = new FileInputStream(new File("src/test/resources/JSON/file.txt"));

    MockMultipartFile file =
        new MockMultipartFile("file", "file.txt", "multipart/form-data", inputFile);

    CandidateDTORequest candidateDTORequest =
        objectMapper.readValue(candidateDTORequestString, CandidateDTORequest.class);
    Candidate newCandidate = new Candidate();
    Position newPosition = new Position();
    CandidateMapper.map(candidateDTORequest, newCandidate, this.beanMapper);
    newPosition.setTitle(POSITION_TITLE);
    newPosition.setId(ID);

    CandidatePosition candidatePosition = new CandidatePosition();

    candidatePosition.setCandidate(newCandidate);
    candidatePosition.setPosition(newPosition);
    newCandidate.getCandidatePositions().add(candidatePosition);
    newPosition.getCandidatePositions().add(candidatePosition);
    Mockito.when(this.positionService.getPosition(POSITION_ID)).thenReturn(newPosition);
    Mockito.when(this.candidateService.createNew(newCandidate, newPosition))
        .thenReturn(newCandidate);

    this.mockMVC
        .perform(MockMvcRequestBuilders.fileUpload(uri).file(file).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME)
            .param("candidateDTORequestString", candidateDTORequestString))
        .andExpect(status().isOk());

    this.candidateController.insertNewCandidate(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, candidateDTORequestString, file);

    Mockito.verify(candidateService, Mockito.times(2)).createNew(newCandidate, newPosition);
    Mockito.verify(positionService, Mockito.times(2)).getPosition(POSITION_ID);
  }

  @Test
  public void testUpdateCandidateDetail() throws Exception {
    String uri = "/api/candidate/updateCandidateDetail";
    FileInputStream inputFile =
        new FileInputStream(new File("src/test/resources/JSON/updatedFile.txt"));

    MockMultipartFile file =
        new MockMultipartFile("file", "file.txt", "multipart/form-data", inputFile);
    Mockito.when(this.candidateService.getCandidate(ID)).thenReturn(candidate);
    this.mockMVC
        .perform(MockMvcRequestBuilders.fileUpload(uri).file(file).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("idCandidate", ID))
        .andExpect(status().isOk());

    this.candidateController.updateCandidateDetail(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, ID, file);
    Mockito.verify(this.candidateService, Mockito.times(2)).getCandidate(ID);
    Candidate updatedCandidate = new Candidate();
    beanMapper.map(candidate, updatedCandidate);
    updatedCandidate.getCandidateDetail().setContent(file.getBytes());
    Assert.assertArrayEquals(candidate.getCandidateDetail().getContent(),
        updatedCandidate.getCandidateDetail().getContent());
    Mockito.verify(this.candidateService, Mockito.times(2)).updateCandidateDetail(updatedCandidate);
  }

  @Test
  public void testUpdateCandidatesStatus() throws Exception {
    String uri = "/api/candidate/updateCandidateStatus";
    String content =
        "{\"candidates\":[{\"emailAddress\":\"izal@zal.zal\",\"firstName\":\"z\",\"lastName\":\"z\",\"phoneNumber\": \"2\",\"positionId\":\"1\"}],\"position\":{\"title\":\"title\",\"id\":\"1\"},\"status\":\"APPLY\"}";
    CandidatesPositionStatusDTOWrapper contentWrapper = new CandidatesPositionStatusDTOWrapper();
    contentWrapper.setCandidates(Mockito.anyList());
    PositionDTORequest position = new PositionDTORequest();
    position.setId("1");
    position.setTitle("title");
    Position post = new Position();
    post.setId("1");
    post.setTitle("title");
    Status stat = Status.APPLY;
    contentWrapper.setPosition(position);
    contentWrapper.setStatus(StatusDTORequest.APPLY);
    StatusDTORequest status = StatusDTORequest.APPLY;
    this.mockMVC
        .perform(MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON_VALUE).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).content(content))
        .andExpect(MockMvcResultMatchers.status().isOk());
    // this.candidateService.updateCandidateStatusBulk(candidates, position, status);
    this.candidateController.updateCandidatesStatus(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, contentWrapper);
    Mockito.verify(this.candidateService, Mockito.times(2)).updateCandidateStatusBulk(candidates,
        post, stat);
  }

}
