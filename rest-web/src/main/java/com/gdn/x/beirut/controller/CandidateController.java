package com.gdn.x.beirut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.services.CandidateService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/candidate")
@Api(value = "CandidateController", description = "Controller untuk Candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;
  @Autowired
  private ObjectMapper mapper;

  @RequestMapping(value = "/api/position/insertNewPosition", method = RequestMethod.POST,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "insert new position", notes = "memasukan posisi baru.")
  @ResponseBody
  public GdnRestListResponse<CandidateDTOResponse> getAllMahasiswaDetailStatus(
      @RequestParam String clientId, @RequestParam String storeId, @RequestParam String requestId,
      @RequestParam String channelId, @RequestParam String username) {
    return null;
  }
}
