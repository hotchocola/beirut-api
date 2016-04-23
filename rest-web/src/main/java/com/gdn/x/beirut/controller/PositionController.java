package com.gdn.x.beirut.controller;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.response.PositionDTOResponse;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.services.PositionService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/position/")
@Api(value = "PositionController")
public class PositionController {

  @Autowired
  private PositionService positionService;

  @Autowired
  private Mapper dozerMapper;

  @RequestMapping(value = "/api/position/deletePosition", method= RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "delete position",notes="menghapus posisi.")
  @ResponseBody
  public GdnRestListResponse<PositionDTOResponse> deletePosition(@RequestParam String clientId, @RequestParam String storeId,
      @RequestParam String requestId, @RequestParam String channelId, @RequestParam String username, @RequestBody List<String> ids){
     List<Position> pos = this.positionService.markForDeletePosition(ids);
     List<PositionDTOResponse> posis = new ArrayList<PositionDTOResponse>();
     for(int i=0; i< ids.size(); i++){
       PositionDTOResponse pr = new PositionDTOResponse();
       dozerMapper.map(pos.get(i), pr);
       posis.add(pr);
     }
     return new GdnRestListResponse<PositionDTOResponse>(posis, new PageMetaData(50, 0, pos.size()), requestId);
  }

  @RequestMapping(value = "/api/position/updatePosition", method= RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "update position",notes="mengganti posisi.")
  @ResponseBody
  public GdnRestSingleResponse<PositionDTOResponse> updatePosition(@RequestParam String clientId, @RequestParam String storeId,
      @RequestParam String requestId, @RequestParam String channelId, @RequestParam String username, @RequestParam String id, @RequestBody PositionDTORequest posreq){
    Position pos = new Position();
    dozerMapper.map(posreq, pos);
    this.positionService.updatePositionTitle(id, pos.getTitle());
    PositionDTOResponse posres = new PositionDTOResponse();

    return new GdnRestSingleResponse(posres, requestId);
  }
}
