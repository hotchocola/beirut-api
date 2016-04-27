package com.gdn.x.beirut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdn.x.beirut.services.CandidateService;
import com.wordnik.swagger.annotations.Api;

@Controller
@RequestMapping(value = "/api/candidate")
@Api(value = "CandidateController", description = "Controller untuk Candidate")
public class CandidateController {

  @Autowired
  private CandidateService candidateService;
  @Autowired
  private ObjectMapper mapper;
}
