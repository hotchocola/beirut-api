package com.gdn.x.beirut.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.base.mapper.GdnMapper;
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
import com.gdn.x.beirut.entities.StatusLog;
import com.gdn.x.beirut.services.CandidateService;
import com.gdn.x.beirut.services.PositionService;

public class CandidateControllerTest {
  private static final String STORE_ID = "storeId";
  private static final String CLIENT_ID = "clientId";
  private static final String CHANNEL_ID = "channelId";
  private static final String REQUEST_ID = "requestId";
  private static final String USERNAME = "username";
  private static final String ID = "ID_ini";
  private static final String PHONE_NUMBER = "123";
  private static final String POSITION_ID = "22bf3cdb-d49e-4534-af52-9ce28064e7f3";
  private static final String POSITION_TITLE = "POS_TITLE";
  private static final String PHONENUM = "123";
  private static final String EMAIL = "email@email.email";
  private static final Pageable pageable = new PageRequest(0, 4);
  private ObjectMapper objectMapper;

  private GdnMapper gdnMapper;
  private MockMvc mockMVC;
  private final Candidate candidate = new Candidate();
  private final List<Candidate> candidates = new LinkedList<>();
  private final Page<Candidate> pageCandidate = new PageImpl(candidates, pageable, 10);
<<<<<<< HEAD
  private final Long start = (long) 14062016;
  private final Long end = (long) 16062016;
  private final String page = "1";
=======
  private final Long start = (long) 1434323587;
  private final Long end = (long) 1465945987;
  private final String page = "0";
>>>>>>> ce24c7ebcfa5f4dd9f18d352995446a91e383e52
  private final String size = "4";

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
    gdnMapper = new GdnMapper() {

      @Override
      public <T> T deepCopy(Object source, Class<T> destinationClass) {
        Mapper mapper = new DozerBeanMapper();
        T destination;
        try {
          destination = destinationClass.newInstance();
        } catch (InstantiationException e) {
          return (T) source;
        } catch (IllegalAccessException e) {
          return (T) source;
        }
        mapper.map(source, destination);
        return destination;

      }
    };
    objectMapper = new ObjectMapper();
    candidateController.setGdnMapper(gdnMapper);
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
      CandidateDTOResponse newCandidateDTORes =
          gdnMapper.deepCopy(candidate, CandidateDTOResponse.class);
      candidateResponse.add(newCandidateDTORes);
    }

  }

  @Test
  public void searchByCreatedDateBetweenTest() throws Exception {
    String uri = "/api/candidate/findCandidateByCreatedDateBetweenAndStoreId";
    Date startDate = new Date(start);
    Date endDate = new Date(end);
    String startString = start.toString();
    String endString = end.toString();

    Mockito.when(this.candidateService.searchByCreatedDateBetweenAndStoreId(startDate, endDate,
        STORE_ID, pageable)).thenReturn(pageCandidate);
    GdnRestListResponse<CandidateDTOResponse> res = this.candidateController
        .findCandidateByCreatedDateBetweenAndStoreId(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
            USERNAME, start, end, Integer.parseInt(page), Integer.parseInt(size));
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("start", startString)
            .param("end", endString).param("page", page).param("size", size))
        .andExpect(status().isOk()).andReturn().equals(res);

    GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
        candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
    for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchByCreatedDateBetweenAndStoreId(startDate, endDate, STORE_ID, pageable);
  }

  @Test
  public void testApplyNewPosition() throws Exception {
    String uri = "/api/candidate/applyNewPosition";

    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setEmailAddress("blahblah");
    candidate.setFirstName("blahblah");
    candidate.setLastName("blahblah");
    CandidateDTORequest candidateDTORequest =
        gdnMapper.deepCopy(candidate, CandidateDTORequest.class);
    List<Position> positions = new ArrayList<>();
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Position position = new Position();
      position.setTitle("Title : " + i);
      position.setId(POSITION_ID + i);
      position.setStoreId(STORE_ID);
      positions.add(position);
      positionIds.add(POSITION_ID + i);
    }
    ListStringRequest listStringRequest = new ListStringRequest();
    listStringRequest.setValues(positionIds);

    String listStringJson =
        FileUtils.readFileToString(new File("src/test/resources/JSON/markForDeleteJSON.json"));
    String candidateDTORequestJson = FileUtils
        .readFileToString(new File("src/test/resources/JSON/candidateDTORequestString.txt"));
    this.mockMVC
        .perform(MockMvcRequestBuilders.post(uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME)
            .content(candidateDTORequestJson).content(listStringJson)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.candidateController.applyNewPosition(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME,
        candidateDTORequest, listStringRequest);

    Mockito.verify(this.candidateService, Mockito.times(1)).applyNewPosition(Mockito.matches(ID),
        Mockito.anyListOf(String.class));
  }

  @Test
  public void testFindCandidateByEmailAddressAndStoreId() throws Exception {
    String uri = "/api/candidate/findCandidateByEmailAddressAndStoreId";
    Candidate cand = new Candidate();
    cand.setId(ID);
    cand.setStoreId(STORE_ID);
    cand.setEmailAddress(EMAIL);
    List<Candidate> cands = new ArrayList<Candidate>();
    cands.add(cand);
    Mockito.when(this.candidateService.searchCandidateByEmailAddressAndStoreId(EMAIL, STORE_ID))
        .thenReturn(cand);
    this.mockMVC
        .perform(
            MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID).param("storeId", STORE_ID)
                .param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
                .param("username", USERNAME).param("emailAddress", EMAIL))
        .andExpect(MockMvcResultMatchers.status().isOk());

    this.candidateController.findCandidateByEmailAddressAndStoreId(CLIENT_ID, STORE_ID, REQUEST_ID,
        CHANNEL_ID, USERNAME, EMAIL);
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchCandidateByEmailAddressAndStoreId(EMAIL, STORE_ID);
  }

  // @RequestMapping(value = "findCandidateByFirstNameContainAndStoreId", method =
  // RequestMethod.GET,
  // produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  // @ApiOperation(value = "Find candidate by their first name", notes = "")
  // @ResponseBody
  // public GdnRestListResponse<CandidateDTOResponse> findCandidateByFirstNameContainAndStoreId(
  // @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
  // @RequestParam String channelId, @RequestParam String username, @RequestParam String firstName)
  // throws Exception {
  // List<Candidate> candidates =
  // this.candidateService.searchByFirstNameContainAndStoreId(firstName, storeId);
  // List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
  // for (Candidate candidate : candidates) {
  // CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
  // CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
  // candidateResponse.add(newCandidateDTORes);
  // }
  // return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
  // new PageMetaData(50, 0, candidateResponse.size()), requestId);
  // }
  @Test
  public void testFindCandidateByFirstNameContainAndStoreId() throws Exception {
    List<Candidate> candidates = new ArrayList<>();
    Candidate newCandidate = new Candidate();
    newCandidate.setFirstName("John");
    newCandidate.setLastName("Doe");
    newCandidate.setEmailAddress("john@doemail.com");
    Candidate newCandidate2 = new Candidate();
    newCandidate2.setFirstName("Johnathan");
    newCandidate2.setLastName("Mayer");
    Candidate newCandidate3 = new Candidate();
    newCandidate3.setFirstName("FoJohna");
    newCandidate3.setLastName("Gers");
    candidates.add(newCandidate);
    candidates.add(newCandidate2);
    candidates.add(newCandidate3);
    Page<Candidate> pageCandidate =
        new PageImpl(candidates, PageableHelper.generatePageable(0, 4), 10);
    String uri = "/api/candidate/findCandidateByFirstNameContainAndStoreId";
    Mockito
        .when(this.candidateService.searchByFirstNameContainAndStoreId("John", STORE_ID, pageable))
        .thenReturn(pageCandidate);
    this.mockMVC.perform(MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID)
        .param("storeId", STORE_ID).param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
        .param("username", USERNAME).param("firstName", "John").param("page", page)
        .param("size", size)).andExpect(status().isOk());
    GdnRestListResponse<CandidateDTOResponse> res = this.candidateController
        .findCandidateByFirstNameContainAndStoreId(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
            USERNAME, "John", Integer.parseInt(page), Integer.parseInt(size));;
    Assert.assertTrue(res.getContent().get(0).getFirstName().contains("John"));
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchByFirstNameContainAndStoreId("John", STORE_ID, pageable);
  }

  @Test(expected = Exception.class)
  public void testFindCandidateByFirstNameContainAndStoreIdAndReturnException() throws Exception {

    String uri = "/api/candidate/findCandidateByFirstNameContainAndStoreId";
    Mockito
        .when(this.candidateService.searchByFirstNameContainAndStoreId("John", STORE_ID, pageable))
        .thenThrow(new Exception());
    this.mockMVC.perform(MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID)
        .param("storeId", STORE_ID).param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
        .param("username", USERNAME).param("firstName", "John").param("page", page)
        .param("size", size)).andExpect(status().isOk());
    GdnRestListResponse<CandidateDTOResponse> res = this.candidateController
        .findCandidateByFirstNameContainAndStoreId(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
            USERNAME, CHANNEL_ID, Integer.parseInt(page), Integer.parseInt(size));
    Assert.assertTrue(res.getContent().get(0).getFirstName().contains("John"));
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchByFirstNameContainAndStoreId("John", STORE_ID, pageable);
  }

  @Test
  public void testFindCandidateById() throws Exception {
    String uri = "/api/candidate/findCandidateByIdDeprecated";
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
  public void testFindCandidateByIdAndStoreIdEager() throws Exception {
    String uri = "/api/candidate/findCandidateByIdAndStoreIdEager";
    Candidate cand = new Candidate();
    cand.setStoreId(STORE_ID);
    cand.setId(ID);
    Set<CandidatePosition> candPositions = new HashSet<CandidatePosition>();
    Position position = new Position();
    position.setId(ID);
    position.setTitle("koko");
    candPositions.add(new CandidatePosition(cand, position));
    cand.setCandidatePositions(candPositions);
    position.setCandidatePositions(candPositions);
    Mockito.when(this.candidateService.getCandidateByIdAndStoreIdEager(ID, STORE_ID))
        .thenReturn(cand);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("idCandidate", ID))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.candidateController.findCandidateByIdAndStoreIdEager(CLIENT_ID, STORE_ID, REQUEST_ID,
        CHANNEL_ID, USERNAME, ID);
    Mockito.verify(this.candidateService, Mockito.times(2)).getCandidateByIdAndStoreIdEager(ID,
        STORE_ID);
  }

  @Test
  public void testFindCandidateByIdAndStoreIdLazy() throws Exception {
    String uri = "/api/candidate/findCandidateByIdAndStoreIdLazy";
    Candidate cand = new Candidate();
    cand.setStoreId(STORE_ID);
    cand.setId(ID);
    Mockito.when(this.candidateService.getCandidateByIdAndStoreIdLazy(ID, STORE_ID))
        .thenReturn(cand);

    this.mockMVC
        .perform(MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).param("idCandidate", ID))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.candidateController.findCandidateByIdAndStoreIdLazy(CLIENT_ID, STORE_ID, REQUEST_ID,
        CHANNEL_ID, USERNAME, ID);
    Mockito.verify(this.candidateService, Mockito.times(2)).getCandidateByIdAndStoreIdLazy(ID,
        STORE_ID);
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
    for (CandidateDTOResponse candidateDTOResponse : res.getContent()) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchCandidateByPhoneNumber(PHONE_NUMBER);
  }

  @Test
  public void testFindCandidateByPhoneNumberContainAndStoreId() throws Exception {
    String uri = "/api/candidate/findCandidateByPhoneNumberContainAndStoreId";

    Mockito.when(this.candidateService.searchCandidateByPhoneNumberContainAndStoreId(PHONENUM,
        STORE_ID, pageable)).thenReturn(pageCandidate);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME)
            .param("phoneNumber", PHONENUM).param("page", page).param("size", size))
        .andExpect(status().isOk());
    GdnRestListResponse<CandidateDTOResponse> res = this.candidateController
        .findCandidateByPhoneNumberContainAndStoreId(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
            USERNAME, PHONENUM, Integer.parseInt(page), Integer.parseInt(size));
    GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
        candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
    for (CandidateDTOResponse candidateDTOResponse : res.getContent()) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2))
        .searchCandidateByPhoneNumberContainAndStoreId(PHONENUM, STORE_ID, pageable);
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
    for (CandidateDTOResponse candidateDTOResponse : res.getContent()) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2)).getAllCandidates();
  }

  // List<CandidateDTOResponse> candidatesDTO = new ArrayList<CandidateDTOResponse>();
  // List<Candidate> candidates = this.candidateService.getAllCandidatesByStoreId(storeId);
  // for (Candidate candidate : candidates) {
  // CandidateDTOResponse candidateDTOResponse = new CandidateDTOResponse();
  // CandidateMapper.mapLazy(candidate, candidateDTOResponse, dozerMapper);
  // }
  // return new GdnRestListResponse<CandidateDTOResponse>(candidatesDTO,
  // new PageMetaData(50, 0, candidatesDTO.size()), requestId);
  // }
  @Test
  public void testGetAllCandidateByStoreId() throws Exception {
    String uri = "/api/candidate/getAllCandidateByStoreId";
    List<Candidate> candidates = new ArrayList<Candidate>();
    candidates.add(this.candidate);
    Mockito.when(this.candidateService.getAllCandidatesByStoreId(STORE_ID)).thenReturn(candidates);
    this.mockMVC
        .perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME))
        .andExpect(MockMvcResultMatchers.status().isOk());

    Mockito.verify(this.candidateService, Mockito.times(1)).getAllCandidatesByStoreId(STORE_ID);
  }


  @Test
  public void testGetAllCandidateWithPageable() throws Exception {
    String uri = "/api/candidate/getAllCandidatesWithPageable";
    Mockito.when(this.candidateService.getAllCandidatesWithPageable(STORE_ID, pageable))
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
    for (CandidateDTOResponse candidateDTOResponse : res.getContent()) {
      expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
    }
    Mockito.verify(this.candidateService, Mockito.times(2)).getAllCandidatesWithPageable(STORE_ID,
        pageable);
  }

  @Test
  public void testGetCandidatePositionDetailWithLogs() throws Exception {
    String uri = "/api/candidate/getCandidatePositionDetailWithLogs";
    CandidatePosition candPost = new CandidatePosition();
    candPost.setId(ID);
    candPost.setStatus(Status.CEO);
    Candidate cand = new Candidate();
    cand.setId(ID);
    cand.setStoreId(ID);
    Position post = new Position();
    post.setId(ID);
    post.setStoreId(ID);
    candPost.setCandidate(cand);
    candPost.setPosition(post);
    Set<CandidatePosition> sets = new HashSet<CandidatePosition>();
    sets.add(candPost);
    cand.setCandidatePositions(sets);
    post.setCandidatePositions(sets);
    StatusLog statusLog = new StatusLog();
    statusLog.setStatus(Status.CALL_CANDIDATE);
    StatusLog statusLog2 = new StatusLog();
    statusLog2.setStatus(Status.HRD);
    candPost.getStatusLogs().add(statusLog);
    candPost.getStatusLogs().add(statusLog2);

    Mockito.when(this.candidateService.getCandidatePositionWithLogs(ID, ID)).thenReturn(candPost);
    this.mockMVC.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON).param("clientId", CLIENT_ID)
        .param("storeId", STORE_ID).param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
        .param("username", USERNAME).param("idCandidate", ID).param("idPosition", ID))
        .andExpect(MockMvcResultMatchers.status().isOk());
    this.candidateController.getCandidatePositionDetailWithLogs(CLIENT_ID, STORE_ID, REQUEST_ID,
        CHANNEL_ID, USERNAME, ID, ID);
    Mockito.verify(this.candidateService, Mockito.times(2)).getCandidatePositionWithLogs(ID, ID);
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
    Candidate newCandidate = gdnMapper.deepCopy(candidateDTORequest, Candidate.class);

    List<Position> positions = new ArrayList<>();
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      Position position = new Position();
      position.setTitle("Title : " + i);
      position.setId(POSITION_ID + i);
      position.setStoreId(STORE_ID);
      positions.add(position);
      positionIds.add(POSITION_ID + i);
    }
    Mockito.when(
        this.candidateService.createNew(Mockito.eq(newCandidate), Mockito.anyListOf(String.class)))
        .thenReturn(newCandidate);

    this.mockMVC
        .perform(MockMvcRequestBuilders.fileUpload(uri).file(file).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME)
            .param("candidateDTORequestString", candidateDTORequestString))
        .andExpect(status().isOk());

    this.candidateController.insertNewCandidate(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID,
        USERNAME, candidateDTORequestString, file);
    for (Position position : positions) {
      CandidatePosition candidatePosition = new CandidatePosition();
      candidatePosition.setPosition(position);
      candidatePosition.setCandidate(newCandidate);
      newCandidate.getCandidatePositions().add(candidatePosition);
      position.getCandidatePositions().add(candidatePosition);
    }
    Mockito.verify(candidateService, Mockito.times(2)).createNew(newCandidate, positionIds);
  }


  @Test
  public void testMarkForDelete() throws Exception {
    String uri = "/api/candidate/markForDelete";
    String json =
        FileUtils.readFileToString(new File("src/test/resources/JSON/markForDeleteJSON.json"));
    ListStringRequest listStringIds = objectMapper.readValue(json, ListStringRequest.class);
    Mockito.doNothing().when(this.positionService).markForDeletePosition(Mockito.matches(STORE_ID),
        Mockito.anyListOf(String.class));
    this.mockMVC
        .perform(MockMvcRequestBuilders.post(uri).param("clientId", CLIENT_ID)
            .param("storeId", STORE_ID).param("requestId", REQUEST_ID)
            .param("channelId", CHANNEL_ID).param("username", USERNAME).content(json)
            .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    this.candidateService.markForDelete(listStringIds.getValues());
    Mockito.verify(this.candidateService, Mockito.times(2))
        .markForDelete(Mockito.anyListOf(String.class));
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
    Candidate updatedCandidate = gdnMapper.deepCopy(candidate, Candidate.class);
    updatedCandidate.getCandidateDetail().setContent(file.getBytes());
    Assert.assertArrayEquals(candidate.getCandidateDetail().getContent(),
        updatedCandidate.getCandidateDetail().getContent());
    Mockito.verify(this.candidateService, Mockito.times(2)).updateCandidateDetail(STORE_ID,
        updatedCandidate);

  }

  @Test
  public void testUpdateCandidatesStatus() throws Exception {
    String uri = "/api/candidate/updateCandidateStatus";
    String content = "{  \"values\": [    \"1\" ]}";
    // CandidatesPositionDTOWrapper contentWrapper = new CandidatesPositionDTOWrapper();
    List<String> idCandidates = new ArrayList<String>();
    idCandidates.add("1");
    String idPosition = "1";
    Mockito.doNothing().when(this.candidateService).updateCandidateStatusBulk(STORE_ID,
        idCandidates, idPosition, Status.APPLY);
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

    Mockito.verify(this.candidateService, Mockito.times(2)).updateCandidateStatusBulk(STORE_ID,
        idCandidates, idPosition, Status.APPLY);
  }
}
