package com.gdn.x.beirut.controller;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;

import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.entities.Candidate;


public class CandidateMapper {
  // public static void map(Candidate candidate, CandidateDTOResponse candidateDTOResponse,
  // Mapper dozerMapper) {
  // dozerMapper.map(candidate, candidateDTOResponse);
  // CandidateDetailDTOResponse detilDTOres =
  // new CandidateDetailDTOResponse(candidate.getCandidateDetail().getId());
  // dozerMapper.map(candidate.getCandidateDetail(), detilDTOres);
  // }

  public static void map(CandidateDTORequest candidateDTORequest, Candidate candidate,
      Mapper dozerMapper) {
    dozerMapper.map(candidateDTORequest, candidate);
  }

  // public static void map(List<String> idCandidates, String idPosition,
  // CandidatesPositionDTOWrapper objWrapper, Mapper dozerMapper) {
  // for (String id : objWrapper.getIdCandidates()) {
  // idCandidates.add(id);
  // }
  // dozerMapper.map(objWrapper.getPosition(), position);
  // }

  public static void mapLazy(Candidate candidate, CandidateDTOResponse candidateDTOResponse,
      Mapper dozerMapper) {
    BeanUtils.copyProperties(candidate, candidateDTOResponse, "candidateDetail");
  }

}
