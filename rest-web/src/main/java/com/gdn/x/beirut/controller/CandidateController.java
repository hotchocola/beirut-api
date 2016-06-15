package com.gdn.x.beirut.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.common.web.param.PageableHelper;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateDetailDTOResponse;
import com.gdn.x.beirut.dto.response.CandidatePositionDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;
import com.gdn.x.beirut.services.CandidateService;
import com.gdn.x.beirut.services.PositionService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/candidate/")
@Api(value = "CandidateController", description = "Controller untuk Candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;

  @Autowired
  private PositionService positionService;

  @Autowired
  private Mapper dozerMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @RequestMapping(value = "applyNewPosition", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Apply new Position", notes = "melamar posisi baru.")
  @ResponseBody
  public GdnBaseRestResponse applyNewPosition(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam String candidateDTORequestString,
      @RequestParam String positionDTORequestString) throws Exception {
    CandidateDTORequest candidateDTORequest =
        objectMapper.readValue(candidateDTORequestString, CandidateDTORequest.class);
    PositionDTORequest positionDTORequest =
        objectMapper.readValue(positionDTORequestString, PositionDTORequest.class);
    List<Candidate> cands =
        this.candidateService.searchCandidateByEmailAddress(candidateDTORequest.getEmailAddress());
    Candidate newCandidate = cands.get(0);
    Position position = positionService.getPosition(candidateDTORequest.getPositionId());
    CandidatePosition candPos = new CandidatePosition();
    candPos.setPosition(position);
    candPos.setCandidate(newCandidate);
    CandidatePositionDTOResponse canRes = new CandidatePositionDTOResponse();
    this.dozerMapper.map(candPos, canRes);
    candPos.setStoreId(storeId);
    return new GdnBaseRestResponse(true);
  }

  @RequestMapping(value = "deleteCandidate", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Delete Candidate by Id", notes = "delete kandidat berdasarkan id")
  @ResponseBody
  public GdnBaseRestResponse deleteCandidate(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam String id) throws Exception {
    Candidate candidate = this.candidateService.getCandidate(id);

    return new GdnBaseRestResponse();
  }

  @RequestMapping(value = "findCandidateByCreatedDateBetween", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate which created between day x and day y",
      notes = "mencari kandidat yang dibuat pada periode tertentu.")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByCreatedDateBetween(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam Long start,
      @RequestParam Long end) throws Exception {
    Date startDate = new Date(start);
    Date endDate = new Date(end);
    List<Candidate> candidates =
        this.candidateService.searchByCreatedDateBetween(startDate, endDate);
    List<CandidateDTOResponse> candreses = new ArrayList<CandidateDTOResponse>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse canres = new CandidateDTOResponse();
      CandidateMapper.map(candidate, canres, dozerMapper);
      candreses.add(canres);
    }
    return new GdnRestListResponse<>(candreses, new PageMetaData(50, 0, candreses.size()),
        requestId);
  }

  @RequestMapping(value = "findCandidateByEmailAddress", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their email address", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByEmailAddress(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String emailAddress) throws Exception {
    List<Candidate> candidates = this.candidateService.searchCandidateByEmailAddress(emailAddress);
    List<CandidateDTOResponse> candreses = new ArrayList<CandidateDTOResponse>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.map(candidate, newCandidateDTORes, dozerMapper);
      candreses.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candreses, new PageMetaData(50, 0, candreses.size()),
        requestId);
  }

  @RequestMapping(value = "findCandidateByFirstName", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their first name", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByFirstName(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String firstName)
          throws Exception {
    List<Candidate> candidates = this.candidateService.searchByFirstName(firstName);
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.map(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateById", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "mencari kandidat berdasarkan ID",
      notes = "mengeluarkan kandidat dengan ID tersebut.")
  @ResponseBody
  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateById(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String id)
          throws Exception {
    Candidate candidate = this.candidateService.getCandidate(id);
    CandidateDTOResponse candres = new CandidateDTOResponse();
    CandidateMapper.map(candidate, candres, dozerMapper);
    return new GdnRestSingleResponse<CandidateDTOResponse>(candres, requestId);
  }

  @RequestMapping(value = "findCandidateByLastName", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their last name", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByLastName(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String lastName)
          throws Exception {
    List<Candidate> candidates = this.candidateService.searchByLastName(lastName);
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.map(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateByPhoneNumber", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "mencari kandidat berdasarkan nomor telepon",
      notes = "mengeluarkan kandidat dengan nomor telepon tersebut.")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByPhoneNumber(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String phoneNumber) throws Exception {
    List<Candidate> candidates = this.candidateService.searchCandidateByPhoneNumber(phoneNumber);
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.map(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateByPhoneNumberLike", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their phone number that much alike", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByPhoneNumberLike(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String phoneNumber) throws Exception {
    List<Candidate> candidates =
        this.candidateService.searchCandidateByPhoneNumberLike(phoneNumber);
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.map(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateDetail", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Mencari detail kandidat", notes = "")
  @ResponseBody
  public GdnRestSingleResponse<CandidateDetailDTOResponse> findCandidateDetail(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String id)
          throws Exception {
    CandidateDetail candidate = this.candidateService.getCandidateDetail(id);
    CandidateDetailDTOResponse candetres = new CandidateDetailDTOResponse();
    this.dozerMapper.map(candidate, candetres);
    return new GdnRestSingleResponse<>(candetres, requestId);
  }

  @RequestMapping(value = "getAllCandidate", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Get all Candidates", notes = "Mengambil semua kandidat")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> getAllCandidate(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username) {
    List<Candidate> candidates = this.candidateService.getAllCandidates();
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      System.out.println(candidate);
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "getAllCandidateDetailStatus", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Menemukan detail status kandidat", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> getAllCandidateDetailStatus(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username) throws Exception {
    List<Candidate> candidates = this.candidateService.getAllCandidates();
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      System.out.println(candidate);
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "getAllCandidatesWithPageable", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Getting all candidates with pageable", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> getAllCandidateWithPageable(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam int page,
      @RequestParam int size) throws Exception {
    Pageable pageable = PageableHelper.generatePageable(page, size);
    Page<Candidate> pages = this.candidateService.getAllCandidatesWithPageable(pageable);
    List<CandidateDTOResponse> toShow = new ArrayList<>();
    for (Candidate candidate : pages) {
      CandidateDTOResponse newCandidateDTOResponse = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, newCandidateDTOResponse, dozerMapper);
    }
    GdnRestListResponse<CandidateDTOResponse> pageresponse =
        new GdnRestListResponse<>(toShow, new PageMetaData(50, 0, toShow.size()), requestId);
    return pageresponse;
  }

  public PositionService getPositionService() {
    return positionService;
  }

  @RequestMapping(value = "insertNewCandidate", method = RequestMethod.POST,
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Insert new Candidate", notes = "memasukan kandidat baru.")
  @ResponseBody
  public GdnBaseRestResponse insertNewCandidate(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam String candidateDTORequestString,
      @RequestPart MultipartFile file) throws Exception {
    if (file == null || file.getBytes().length == 0) {
      throw new ApplicationException(ErrorCategory.REQUIRED_PARAMETER,
          "file content mustbe present");
    }
    CandidateDTORequest candidateDTORequest =
        objectMapper.readValue(candidateDTORequestString, CandidateDTORequest.class);
    Candidate newCandidate = new Candidate();
    Position position = positionService.getPosition(candidateDTORequest.getPositionId());
    CandidateDetail candidateDetail = new CandidateDetail();
    candidateDetail.setContent(file.getBytes());
    candidateDetail.setCandidate(newCandidate);
    newCandidate.setCandidateDetail(candidateDetail);
    CandidateMapper.map(candidateDTORequest, newCandidate, dozerMapper);
    Candidate existingCandidate = this.candidateService.createNew(newCandidate, position);
    if (existingCandidate.getId() == null) {
      return new GdnBaseRestResponse(false);
    }
    return new GdnBaseRestResponse(requestId);
  }

  public void setDozerMapper(Mapper dm) {
    this.dozerMapper = dm;
  }

  public void setPositionService(PositionService positionService) {
    this.positionService = positionService;
  }

  @RequestMapping(value = "updateCandidateDetail", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Update candidate detail", notes = "")
  @ResponseBody
  public GdnBaseRestResponse updateCandidateDetail(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody Candidate candidate) throws Exception {
    Candidate newCandidate = this.candidateService.getCandidate(candidate.getId());
    dozerMapper.map(candidate, newCandidate);
    newCandidate.setStoreId(storeId);
    this.candidateService.updateCandidateDetail(newCandidate);

    return new GdnBaseRestResponse(requestId);
  }

  @RequestMapping(value = "updateCandidateStatus", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Update candidate status", notes = "")
  @ResponseBody
  public GdnBaseRestResponse updateCandidateStatus(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody Candidate candidate,
      @RequestBody Position position, @RequestBody Status status) throws Exception {
    Candidate can = this.candidateService.getCandidate(candidate.getId());
    Position pos = this.positionService.getPosition(position.getId());

    return new GdnBaseRestResponse(requestId);
  }
}
