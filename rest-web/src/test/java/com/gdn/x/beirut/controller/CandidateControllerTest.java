package com.gdn.x.beirut.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.services.CandidateService;

public class CandidateControllerTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final String STORE_ID = "storeId";
  private static final String CLIENT_ID = "clientId";
  private static final String CHANNEL_ID = "channelId";
  private static final String REQUEST_ID = "requestId";
  private static final String USERNAME = "username";
  private static final String PHONENUM = "123";

  private static final Pageable pageable = new PageRequest(1, 4);

  private Mapper beanMapper;
  private MockMvc mockMVC;
  private final Candidate cand = new Candidate();
  private final List<Candidate> candidates = new LinkedList<>();
  private final Page<Candidate> pageCandidate = new PageImpl(candidates, pageable, 10);
  private final Date start = new Date(1434323587);
  private final Date end = new Date(1465945987);
  private final String page = "1";
  private final String size = "4";

  private final List<CandidateDTOResponse> candidateResponse = new ArrayList<>();

  @Mock
  private CandidateService candidateService;

  @InjectMocks
  private CandidateController candidateController;

  // @Test
  // public void findCandidateByIdTest() throws Exception {
  // String uri = "/api/candidate/getAllCandidate";
  // Mockito.when(this.candidateService.getAllCandidates()).thenReturn(candidates);
  // this.mockMVC.perform(MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID)
  // .param("storeId", STORE_ID).param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
  // .param("username", USERNAME)).andExpect(status().isOk());
  // GdnRestListResponse<CandidateDTOResponse> res = this.candidateController
  // .getAllCandidate(CLIENT_ID, STORE_ID, REQUEST_ID, CHANNEL_ID, USERNAME);
  // GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
  // candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
  // for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
  // expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
  // }
  // Mockito.verify(this.candidateService, Mockito.times(2)).getAllCandidates();
  // }

  // @Test
  // public void findCandidateByPhoneNumberLikeTest() throws Exception {
  // String uri = "/api/candidate/findCandidateByPhoneNumberLike";
  //
  // Mockito.when(this.candidateService.searchCandidateByPhoneNumberLike(PHONENUM))
  // .thenReturn(candidates);
  // this.mockMVC
  // .perform(
  // MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID).param("storeId", STORE_ID)
  // .param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
  // .param("username", USERNAME).param("phoneNumber", PHONENUM))
  // .andExpect(status().isOk());
  // GdnRestListResponse<CandidateDTOResponse> res =
  // this.candidateController.findCandidateByPhoneNumberLike(CLIENT_ID, STORE_ID, REQUEST_ID,
  // CHANNEL_ID, USERNAME, PHONENUM);
  // GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
  // candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
  // for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
  // expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
  // }
  // Mockito.verify(this.candidateService, Mockito.times(2))
  // .searchCandidateByPhoneNumberLike(PHONENUM);
  // }

  @Test
  public void getAllCandidateWithPageableTest() throws Exception {
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

  @Before
  public void initialize() throws Exception {
    initMocks(this);
    this.mockMVC = standaloneSetup(this.candidateController).build();
    beanMapper = new DozerBeanMapper();
    cand.setId("ID");
    cand.setStoreId(STORE_ID);
    cand.setCandidateDetail(new CandidateDetail());
    cand.setPhoneNumber("1234567890");
    cand.setEmailAddress("egaegaega@ega.ega");
    cand.setCreatedBy("ega");
    cand.setLastName("Prianto");
    cand.setFirstName("Ega");
    candidates.add(cand);

    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, beanMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    candidateController.setDozerMapper(beanMapper);

  }

  // @Test
  // public void searchByCreatedDateBetweenTest() throws Exception {
  // String uri = "/api/candidate/findCandidateByCreatedDateBetween";
  // String json = "2016-06-14 23:13:07";
  // String json2 = "2015-06-14 23:13:07";
  // Mockito.when(this.candidateService.searchByCreatedDateBetween(start, end)).thenReturn(cands);
  // this.mockMVC
  // .perform(
  // MockMvcRequestBuilders.get(uri).param("clientId", CLIENT_ID).param("storeId", STORE_ID)
  // .param("requestId", REQUEST_ID).param("channelId", CHANNEL_ID)
  // .param("username", USERNAME).content(json2).accept(MediaType.APPLICATION_JSON)
  // .contentType(MediaType.APPLICATION_JSON).content(json)
  // .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
  // .andExpect(status().isOk());
  // GdnRestListResponse<CandidateDTOResponse> res =
  // this.candidateController.findCandidateByCreatedDateBetween(CLIENT_ID, STORE_ID, REQUEST_ID,
  // CHANNEL_ID, USERNAME, start, end);
  // GdnRestListResponse<CandidateDTOResponse> expectedRes = new GdnRestListResponse<>(
  // candidateResponse, new PageMetaData(50, 0, candidateResponse.size()), REQUEST_ID);
  // for (CandidateDTOResponse candidateDTOResponse : candidateResponse) {
  // expectedRes.getContent().iterator().next().getId().equals(candidateDTOResponse.getId());
  // }
  // Mockito.verify(this.candidateService, Mockito.times(2)).searchByCreatedDateBetween(start, end);
  // }
}
