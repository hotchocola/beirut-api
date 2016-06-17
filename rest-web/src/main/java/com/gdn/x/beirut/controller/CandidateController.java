package com.gdn.x.beirut.controller;

import java.util.ArrayList;
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
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateDetailDTOResponse;
import com.gdn.x.beirut.dto.response.CandidatePositionDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateWithPositionsDTOResponse;
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
    List<Candidate> cands = this.candidateService
        .searchCandidateByEmailAddressAndStoreId(candidateDTORequest.getEmailAddress(), storeId);
    Candidate newCandidate = cands.get(0);
    Position position = positionService.getPosition(storeId, candidateDTORequest.getPositionId());
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

<<<<<<< HEAD
  @RequestMapping(value = "findCandidateByEmailAddress", method = RequestMethod.GET,
=======
  @RequestMapping(value = "findCandidateByCreatedDateBetweenAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate which created between day x and day y",
      notes = "mencari kandidat yang dibuat pada periode tertentu.")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByCreatedDateBetweenAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam Long start,
      @RequestParam Long end) throws Exception {
    Date startDate = new Date(start);
    Date endDate = new Date(end);
    List<Candidate> candidates =
        this.candidateService.searchByCreatedDateBetweenAndStoreId(startDate, endDate, storeId);
    List<CandidateDTOResponse> res = new ArrayList<CandidateDTOResponse>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse candidateDTOResponse = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, candidateDTOResponse, dozerMapper);
      res.add(candidateDTOResponse);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(res, new PageMetaData(50, 0, res.size()),
        requestId);
  }

  @RequestMapping(value = "findCandidateByEmailAddressAndStoreId", method = RequestMethod.GET,
>>>>>>> eab6d17269eba37ec0ded3d44cd5e2ea7f03fa89
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their email address", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByEmailAddressAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String emailAddress) throws Exception {
    List<Candidate> candidates =
        this.candidateService.searchCandidateByEmailAddressAndStoreId(emailAddress, storeId);
    List<CandidateDTOResponse> candreses = new ArrayList<CandidateDTOResponse>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
      candreses.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candreses,
        new PageMetaData(50, 0, candreses.size()), requestId);
  }

  @RequestMapping(value = "findCandidateByFirstNameContainAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their first name", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByFirstNameContainAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String firstName)
          throws Exception {
    List<Candidate> candidates =
        this.candidateService.searchByFirstNameContainAndStoreId(firstName, storeId);
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateByIdDeprecated", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "mencari kandidat berdasarkan ID",
      notes = "mengeluarkan kandidat dengan ID tersebut.")
  @ResponseBody
  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateById(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String id)
          throws Exception {
    Candidate candidate = this.candidateService.getCandidate(id);
    CandidateDTOResponse candres = new CandidateDTOResponse();
    CandidateMapper.mapLazy(candidate, candres, dozerMapper);
    return new GdnRestSingleResponse<CandidateDTOResponse>(candres, requestId);
  }

  @RequestMapping(value = "findCandidateByIdAndStoreIdEager", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "get candidate by id and store id eager with candidate positions",
      notes = "mengambil candidate berdasarkan id dan store id yang diberikan dengan relasi candidatePosition nya")
  @ResponseBody
  public GdnRestSingleResponse<CandidateWithPositionsDTOResponse> findCandidateByIdAndStoreIdEager(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String idCandidate) throws Exception {
    Candidate candidate =
        this.candidateService.getCandidateByIdAndStoreIdEager(idCandidate, storeId);
    // System.out.println("ENTITY : " + candidate.toStringz());
    CandidateWithPositionsDTOResponse candResponse = new CandidateWithPositionsDTOResponse();
    CandidateMapper.map(candidate, candResponse, dozerMapper);
    // System.out.println(" RESPONSE ADA statusnya GAGAGA : " + candResponse.toStringz());
    return new GdnRestSingleResponse<CandidateWithPositionsDTOResponse>(candResponse, requestId);
  }

  @RequestMapping(value = "findCandidateByIdAndStoreIdLazy", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})

  @ApiOperation(value = "get candidate by id and store id lazy",
      notes = "mengambil candidate berdasarkan id dan store id yang diberikan tanpa megambil child/relasi dengan objek lainnya (hanya summary-nya)")
  @ResponseBody
  public GdnRestSingleResponse<CandidateDTOResponse> findCandidateByIdAndStoreIdLazy(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String idCandidate) throws Exception {
    Candidate candidate =
        this.candidateService.getCandidateByIdAndStoreIdLazy(idCandidate, storeId);
    CandidateDTOResponse candResponse = new CandidateDTOResponse();
    CandidateMapper.mapLazy(candidate, candResponse, dozerMapper);
    return new GdnRestSingleResponse<CandidateDTOResponse>(candResponse, requestId);
  }

  @RequestMapping(value = "findCandidateByLastNameContainAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their last name", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByLastNameContainAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String lastName)
          throws Exception {
    List<Candidate> candidates =
        this.candidateService.searchByLastNameContainAndStoreId(lastName, storeId);
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateByPhoneNumber", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE})
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
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }

  @RequestMapping(value = "findCandidateByPhoneNumberContainAndStoreId", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Find candidate by their phone number that much alike", notes = "")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> findCandidateByPhoneNumberContainAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String phoneNumber) throws Exception {
    List<Candidate> candidates =
        this.candidateService.searchCandidateByPhoneNumberContainAndStoreId(phoneNumber, storeId);
    List<CandidateDTOResponse> candidateResponse = new ArrayList<>();
    for (Candidate candidate : candidates) {
      CandidateDTOResponse newCandidateDTORes = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }



  @RequestMapping(value = "findCandidateDetailAndStoreId", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Mencari detail kandidat", notes = "")

  @ResponseBody
  public GdnRestSingleResponse<CandidateDetailDTOResponse> findCandidateDetailAndStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username, @RequestParam String id)
          throws Exception {
    CandidateDetail candidate = this.candidateService.getCandidateDetailAndStoreId(id, storeId);
    CandidateDetailDTOResponse candetres = new CandidateDetailDTOResponse();
    this.dozerMapper.map(candidate, candetres);
    return new GdnRestSingleResponse(candetres, requestId);
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
      // System.out.println(candidate);
      CandidateMapper.mapLazy(candidate, newCandidateDTORes, dozerMapper);
      candidateResponse.add(newCandidateDTORes);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidateResponse,
        new PageMetaData(50, 0, candidateResponse.size()), requestId);
  }


  @RequestMapping(value = "getAllCandidateByStoreId", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "get all candidate dengan store id yang diberikan",
      notes = "Mengembalikan semua kandidat pada store id tertentu")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> getAllCandidateByStoreId(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username) throws Exception {
    List<CandidateDTOResponse> candidatesDTO = new ArrayList<CandidateDTOResponse>();
    List<Candidate> candidates = this.candidateService.getAllCandidatesByStoreId(storeId);
    for (Candidate candidate : candidates) {
      CandidateDTOResponse candidateDTOResponse = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, candidateDTOResponse, dozerMapper);
    }
    return new GdnRestListResponse<CandidateDTOResponse>(candidatesDTO,
        new PageMetaData(50, 0, candidatesDTO.size()), requestId);
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
    Page<Candidate> pages = this.candidateService.getAllCandidatesWithPageable(storeId, pageable);
    List<CandidateDTOResponse> toShow = new ArrayList<>();
    for (Candidate candidate : pages) {
      CandidateDTOResponse newCandidateDTOResponse = new CandidateDTOResponse();
      CandidateMapper.mapLazy(candidate, newCandidateDTOResponse, dozerMapper);
      toShow.add(newCandidateDTOResponse);
    }
    GdnRestListResponse<CandidateDTOResponse> pageresponse =
        new GdnRestListResponse(toShow, new PageMetaData(50, 0, toShow.size()), requestId);
    return pageresponse;
  }

  @RequestMapping(value = "getCandidatePositionDetailWithLogs", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "get candidateposition with logs",
      notes = "Get candidate tertentu dengan history logs nya")
  @ResponseBody
  public GdnRestSingleResponse<CandidatePositionDTOResponse> getCandidatePositionDetailWithLogs(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username,
      @RequestParam String idCandidate, @RequestParam String idPosition) throws Exception {
    CandidatePosition candidatePosition =
        this.candidateService.getCandidatePositionWithLogs(idCandidate, idPosition);
    CandidatePositionDTOResponse candidatePositionResponse = new CandidatePositionDTOResponse();
    // Hibernate.initialize(candidatePosition.getStatusLogs());
    CandidateMapper.map(candidatePosition, candidatePositionResponse, dozerMapper);

    // System.out.println("ID CAND : " + candidatePositionResponse.getCandidate().getId() + "; POST
    // ID : "
    // + candidatePositionResponse.getPosition().getId() + "; STATUS : "
    // + candidatePosition.getStatus());
    // System.out.println(candidatePositionResponse.getStatusLogs().size());
    // for (StatusLogDTOResponse statusLogResponse : candidatePositionResponse.getStatusLogs()) {
    // System.out.println("WUWU : " + statusLogResponse.getStatus());
    // }
    // System.out.println("MASUK : ");
    return new GdnRestSingleResponse<CandidatePositionDTOResponse>(candidatePositionResponse,
        requestId);
  }

  public ObjectMapper getObjectMapper() {
    return objectMapper;
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
    Position position = positionService.getPosition(storeId, candidateDTORequest.getPositionId());
    CandidateDetail candidateDetail = new CandidateDetail();
    candidateDetail.setContent(file.getBytes());
    candidateDetail.setCandidate(newCandidate);
    newCandidate.setCandidateDetail(candidateDetail);
    newCandidate.setStoreId(storeId); // zal
    CandidateMapper.map(candidateDTORequest, newCandidate, dozerMapper);
    Candidate existingCandidate = this.candidateService.createNew(newCandidate, position);
    if (existingCandidate.getId() == null) {
      return new GdnBaseRestResponse(false);
    }
    return new GdnBaseRestResponse(requestId);
  }

  @RequestMapping(value = "markForDelete", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Delete Id yang ada", notes = "Semua yang ada di list akan di hapus")
  @ResponseBody
  public GdnBaseRestResponse markForDelete(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody ListStringRequest idsRequest) throws Exception {
    try {
      this.candidateService.markForDelete(idsRequest.getValues());
      return new GdnBaseRestResponse(requestId);
    } catch (Exception e) {
      return new GdnBaseRestResponse(e.getMessage(), "", false, requestId);
    }

  }

  public void setDozerMapper(Mapper dm) {
    this.dozerMapper = dm;
  }

  public void setObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }


  public void setPositionService(PositionService positionService) {
    this.positionService = positionService;
  }

  @RequestMapping(value = "updateCandidateDetail", method = RequestMethod.POST,
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Update candidate detail", notes = "")
  @ResponseBody
  public GdnBaseRestResponse updateCandidateDetail(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam String idCandidate,
      @RequestPart MultipartFile file) throws Exception {
    if (file == null || file.getBytes().length == 0) {
      throw new ApplicationException(ErrorCategory.REQUIRED_PARAMETER,
          "file content mustbe present");
    }
    Candidate candidate = this.candidateService.getCandidate(idCandidate);
    candidate.getCandidateDetail().setContent(file.getBytes());
    try {
      this.candidateService.updateCandidateDetail(storeId, candidate);
      return new GdnBaseRestResponse(true);
    } catch (Exception e) {
      return new GdnBaseRestResponse(requestId);
    }
  }

  @RequestMapping(value = "updateCandidateStatus", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "update candidate status",
      notes = "Update satu atau lebih Status Candidate dengan Position yang diberikan (Jika punya) menjadi status yang diberikan")
  @ResponseBody
  public GdnBaseRestResponse updateCandidatesStatus(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam Status status, @RequestParam String idPosition,
      @RequestBody ListStringRequest idCandidates) throws Exception {
    // CandidateMapper.map(idCandidates, position, objectWrapper, dozerMapper);
    // System.out.println(objectWrapper.toString());
    // System.out.println(idCandidates.get(0)); // DEBUG
    this.candidateService.updateCandidateStatusBulk(storeId, idCandidates.getValues(), idPosition,
        status);
    return new GdnBaseRestResponse(true);
  }
}
