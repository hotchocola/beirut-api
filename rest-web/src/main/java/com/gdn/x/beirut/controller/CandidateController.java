package com.gdn.x.beirut.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.Position;
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

  @RequestMapping(value = "updateCandidateStatus", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "update candidate status",
      notes = "Update satu atau lebih Status Candidate dengan Position yang diberikan (Jika punya) menjadi status yang diberikan")
  @ResponseBody
  public void updateCandidatesStatus(@RequestParam String clientId, @RequestParam String storeId,
      @RequestParam String requestId, @RequestParam String channelId, @RequestParam String username,
      @RequestParam List<Candidate> candidates) {

  }

}
