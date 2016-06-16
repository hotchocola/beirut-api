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

import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.gdn.x.beirut.dto.request.ListStringRequest;
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

  @RequestMapping(value = "deletePosition", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "delete position", notes = "menghapus posisi.")
  @ResponseBody
  public GdnBaseRestResponse deletePosition(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody ListStringRequest idsToDelete) {
    this.positionService.markForDeletePosition(idsToDelete.getValues());

    return new GdnBaseRestResponse(true);
  }

  @RequestMapping(value = "getAllPosition", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "get all position", notes = "mengambil semua posisi.")
  @ResponseBody
  public GdnRestListResponse<PositionDTOResponse> getAllPosition(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username) {
    List<Position> positions = this.positionService.getAllPosition();
    List<PositionDTOResponse> positionDTOResponses = new ArrayList<PositionDTOResponse>();
    for (Position positiones : positions) {
      PositionDTOResponse positionDTOResponse = new PositionDTOResponse();
      dozerMapper.map(positiones, positionDTOResponse);
      positionDTOResponses.add(positionDTOResponse);
    }

    return new GdnRestListResponse<PositionDTOResponse>(positionDTOResponses,
        new PageMetaData(5, 5, positions.size()), requestId);
  }

  @RequestMapping(value = "getPositionByTitle", method = RequestMethod.GET,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "get position by title", notes = "mengambil semua posisi dengan nama.")
  @ResponseBody
  public GdnRestListResponse<PositionDTOResponse> getPositionByTitle(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam String title) {
    List<Position> positions = this.positionService.getPositionByTitle(title, storeId);
    List<PositionDTOResponse> positionDTOResponses = new ArrayList<PositionDTOResponse>();

    for (Position positiones : positions) {
      PositionDTOResponse positionDTOResponse = new PositionDTOResponse();
      dozerMapper.map(positiones, positionDTOResponse);
      positionDTOResponses.add(positionDTOResponse);
    }

    return new GdnRestListResponse<PositionDTOResponse>(positionDTOResponses,
        new PageMetaData(5, 5, positions.size()), requestId);
  }

  @RequestMapping(value = "insertNewPosition", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "insert new position", notes = "memasukan posisi baru.")
  @ResponseBody
  public GdnBaseRestResponse insertNewPosition(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestBody PositionDTORequest positionDTORequest) {
    Position temp = new Position();
    dozerMapper.map(positionDTORequest, temp);
    // System.out.println("DTO : " + positionDTORequest.toString());
    // System.out.println(temp.toString());
    temp.setStoreId(storeId);
    return new GdnBaseRestResponse(this.positionService.insertNewPosition(temp));
  }

  public void setDozerMapper(Mapper dm) {
    this.dozerMapper = dm;
  }

  @RequestMapping(value = "updatePosition", method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "update position", notes = "mengganti posisi.")
  @ResponseBody
  public GdnBaseRestResponse updatePosition(@RequestParam String clientId,
      @RequestParam String storeId, @RequestParam String requestId, @RequestParam String channelId,
      @RequestParam String username, @RequestParam(required = true) String id,
      @RequestBody PositionDTORequest positionDTORequest) {
    Position pos = new Position();
    dozerMapper.map(positionDTORequest, pos);
    pos.setStoreId(storeId);

    return new GdnBaseRestResponse(
        this.positionService.updatePositionTitle(id, positionDTORequest.getTitle()));
  }
}
