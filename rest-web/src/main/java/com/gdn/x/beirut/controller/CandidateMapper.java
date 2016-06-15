package com.gdn.x.beirut.controller;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;

import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.CandidatesPositionStatusDTOWrapper;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateDetailDTOResponse;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;

public class CandidateMapper {
  public static void map(Candidate candidate, CandidateDTOResponse candidateDTOResponse,
      Mapper dozerMapper) {
    dozerMapper.map(candidate, candidateDTOResponse);
    CandidateDetailDTOResponse detilDTOres =
        new CandidateDetailDTOResponse(candidate.getCandidateDetail().getId());
    dozerMapper.map(candidate.getCandidateDetail(), detilDTOres);
  }

  public static void map(CandidateDTORequest candidateDTORequest, Candidate candidate,
      Mapper dozerMapper) {
    dozerMapper.map(candidateDTORequest, candidate);
  }

  public static void map(List<Candidate> candidate, Position position, Status status,
      CandidatesPositionStatusDTOWrapper objWrapper, Mapper dozerMapper) {
    for (CandidateDTORequest candDTO : objWrapper.getCandidates()) {
      Candidate newCand = new Candidate();
      dozerMapper.map(candDTO, newCand);
      candidate.add(newCand);
    }
    dozerMapper.map(objWrapper.getPosition(), position);
    dozerMapper.map(objWrapper.getStatus(), status);
  }

  public static void mapLazy(Candidate candidate, CandidateDTOResponse candidateDTOResponse,
      Mapper dozerMapper) {
    BeanUtils.copyProperties(candidate, candidateDTOResponse, "candidateDetail");
  }
}
