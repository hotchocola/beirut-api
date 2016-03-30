package com.ega.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ega.dto.MahasiswaDTO;
import com.ega.entities.Mahasiswa;
import com.ega.services.SimpleCRUD;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


@Controller
@RequestMapping(value = "/api/mahasiswa")
@Api(value = "MahasiswaController", description = "Controller API")
public class MahasiswaController {
  @Autowired
  private SimpleCRUD simpleCRUD;

  @RequestMapping(value = "/findByNama", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_XML_VALUE})

  @ResponseBody
  @ApiOperation(value = "cari nama mahasiwa", notes = " blablablablalasjadksahjdhj")
  public GdnRestSingleResponse<MahasiswaDTO> findByNama(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String nama) {
    Mahasiswa m = simpleCRUD.findByNama(nama);
    MahasiswaDTO md = new MahasiswaDTO(m.getId() + "", m.getNama(), m.getNpm());
    GdnRestSingleResponse<MahasiswaDTO> obj =
        new GdnRestSingleResponse<MahasiswaDTO>(md, requestId);
    return obj;
  }

  @RequestMapping(value = "", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_XML_VALUE})

  // buat ubah jadi json/xml
  @ResponseBody
  @ApiOperation(value = "keluarin list mahasiswa", notes = " blablablablalasjadksahjdhj")
  public GdnRestListResponse<MahasiswaDTO> getAll(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam(required = false) String username) {

    ArrayList<MahasiswaDTO> listMah = new ArrayList<MahasiswaDTO>();
    List<Mahasiswa> list = new ArrayList<Mahasiswa>();
    list = simpleCRUD.getAll();
    for (int i = 0; i < list.size(); i++) {
      listMah.add(
          new MahasiswaDTO(list.get(i).getId() + "", list.get(i).getNama(), list.get(i).getNpm()));
    }

    GdnRestListResponse<MahasiswaDTO> obj =
        new GdnRestListResponse<MahasiswaDTO>(listMah, new PageMetaData(50, 0, 100), requestId);
    return obj;
  }

}
