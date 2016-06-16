package com.gdn.x.beirut.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.web.param.PageableHelper;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.ListStringRequest;
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
  private static final String PHONENUM = "123";
  private static final Pageable pageable = PageableHelper.generatePageable(1, 4);
  private ObjectMapper objectMapper;

  private Mapper beanMapper;
  private MockMvc mockMVC;
  private final Candidate candidate = new Candidate();
  private final List<Candidate> candidates = new LinkedList<>();
  private final Page<Candidate> pageCandidate = new PageImpl(candidates, pageable, 10);
  private final Long start = (long) 1434323587;
  private final Long end = (long) 1465945987;
  private final String page = "1";
  private final String size = "4";

  private final List<CandidateDTOResponse> candidateResponse = new ArrayList<>();

  @Mock
  private CandidateService candidateService;

  @Mock
  private PositionService positionService;

  @InjectMocks
  private CandidateController candidateController;


  @Test
  public void findCandidateByPhoneNumberLikeTest() throws Exception {
    String uri = "/api/candidate/findCandidateByPhoneNumberLike";

    Mockito.when(this.candidateService.searchCandidateByPhoneNumberLike(PHONENUM))
        .thenReturn(candidates);
    this.mockMVC
        .perform(
            MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID).param("storeId", STORE_ID)
                .param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
                .param("username", USERNAME).param("phoneNumber", PHONENUM))
        .andExpect(status().isOk());
    GdnRestListResponse<CandidateDTOResponse> res =
        this.candidateController.findCandidateByPhoneNumberLike(CLIENT_ID, STORE_ID, REQUEST_ID,
            CHANNEL_ID, USERNAME, PHONENUM);
    GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
        candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
    for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchCandidateByPhoneNumberLike(PHONENUM);
  }

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
  public void testFindCandidateByPhoneNumberLike() throws Exception {
    String uri = "/api/candidate/findCandidateByPhoneNumberLike";

    Mockito.when(this.candidateService.searchCandidateByPhoneNumberLike(PHONENUM))
        .thenReturn(candidates);
    this.mockMVC
        .perform(
            MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID).param("storeId", STORE_ID)
                .param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
                .param("username", USERNAME).param("phoneNumber", PHONENUM))
        .andExpect(status().isOk());
    GdnRestListResponse<CandidateDTOResponse> res =
        this.candidateController.findCandidateByPhoneNumberLike(CLIENT_ID, STORE_ID, REQUEST_ID,
            CHANNEL_ID, USERNAME, PHONENUM);
    GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
        candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
    for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchCandidateByPhoneNumberLike(PHONENUM);
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
  public void testGetAllCandidateWithPageable() throws Exception {
    String uri = "/api/candidate/getAllCandidatesWithPageable";
    Mockito.when(this.candidateService.getAllCandidatesWithPageable(pageable))
        .thenReturn(pageCandidate);
    this.mockMVC
        .perform(
            MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID).param("storeId", STORE_ID)
                .param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
                .param("username", USERNAME).param("page", page).param("size", size))
        .andExpect(status().isOk());
    GdnRestListResponse<CandidateDTOResponse> res =
        this.candidateController.getAllCandidateWithPageable(CLIENT_ID, STORE_ID, REQUEST_ID,
            CHANNEL_ID, USERNAME, Integer.parseInt(page), Integer.parseInt(size));
    GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
        candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
    for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2)).getAllCandidatesWithPageable(pageable);
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
    Mockito.when(this.positionService.getPosition(STORE_ID, POSITION_ID)).thenReturn(newPosition);
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
    Mockito.verify(positionService, Mockito.times(2)).getPosition(STORE_ID, POSITION_ID);
  }

  @Test
  public void testMarkForDelete() throws Exception {
    String uri = "/api/candidate/markForDelete";
    String json =
        FileUtils.readFileToString(new File("src/test/resources/JSON/markForDeleteJSON.json"));
    ListStringRequest listStringIds = objectMapper.readValue(json, ListStringRequest.class);
    Mockito.doNothing().when(this.positionService).markForDeletePosition(Mockito.matches(STORE_ID),
        Mockito.anyList());
    this.mockMVC
        .perform(MockMvcRequestBuilders.post(uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).content(json)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    this.candidateService.markForDelete(listStringIds.getValues());
    Mockito.verify(this.candidateService, Mockito.times(2)).markForDelete(Mockito.anyList());
  }

  @Test
  public void testSearchByCreatedDateBetween() throws Exception {
    String uri = "/api/candidate/findCandidateByCreatedDateBetween";
    Date startDate = new Date(start);
    Date endDate = new Date(end);
    String startString = start.toString();
    String endString = end.toString();

    GdnRestListResponse<CandidateDTOResponse> res =
        this.candidateController.findCandidateByCreatedDateBetween(CLIENT_ID, STORE_ID, REQUEST_ID,
            CHANNEL_ID, USERNAME, start, end);

    Mockito.when(this.candidateService.searchByCreatedDateBetween(startDate, endDate))
        .thenReturn(candidates);
    this.mockMVC
        .perform(
            MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID).param("storeId", STORE_ID)
                .param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
                .param("username", USERNAME).param("start", startString).param("end", endString))
        .andExpect(status().isOk()).andReturn().equals(res);

    GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
        candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
    for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2)).searchByCreatedDateBetween(startDate,
        endDate);
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
    String content = "{  \"values\": [    \"1\" ]}";
    // CandidatesPositionDTOWrapper contentWrapper = new CandidatesPositionDTOWrapper();
    List<String> idCandidates = new ArrayList<String>();
    idCandidates.add("1");
    String idPosition = "1";
    Mockito.doNothing().when(this.candidateService).updateCandidateStatusBulk(idCandidates,
        idPosition, Status.APPLY);
    ListStringRequest newListStringRequest = new ListStringRequest();
    newListStringRequest.setValues(idCandidates);
    this.mockMVC
        .perform(MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("status", "APPLY")
            .param("idPosition", idPosition).content(content))
        .andExpect(MockMvcResultMatchers.status().isOk());

    this.candidateController.updateCandidatesStatus(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, Status.APPLY, idPosition, newListStringRequest);

    Mockito.verify(this.candidateService, Mockito.times(2)).updateCandidateStatusBulk(idCandidates,
        idPosition, Status.APPLY);
  }
}
