package com.ega.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

  @RequestMapping(value = "/deleteMahasiswa", method = RequestMethod.POST,
      produces = {MediaType.APPLICATION_XML_VALUE})

  @ResponseBody
  @ApiOperation(value = "delete mahasiswa berdasarkan objek yang diberikan",
      notes = "asasadasdasdsad")
  public void deleteMahasiswa(@RequestParam String storeId, @RequestParam String channelId,
      @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username,
      @RequestBody Mahasiswa mahasiswa) {
    Mahasiswa ma = new Mahasiswa();
    ma.setId(mahasiswa.getId());
    ma.setNama(mahasiswa.getNama());
    ma.setNpm(mahasiswa.getNpm());
    if (!(simpleCRUD.findMahasiswaById(ma.getId()).equals(""))) {
      simpleCRUD.deleteMahasiswa(ma);
    }
  }

  @RequestMapping(value = "/deleteMahasiswaById", method = RequestMethod.POST,
      produces = {MediaType.APPLICATION_XML_VALUE})

  @ResponseBody
  @ApiOperation(value = "delete mahasiswa berdasarkan id yang diberikan", notes = "asasadasdasdsad")
  public void deleteMahasiswaById(@RequestParam String storeId, @RequestParam String channelId,
      @RequestParam String clientId, @RequestParam String requestId, @RequestParam String username,
      @RequestParam String id) {
    Mahasiswa ma = new Mahasiswa();
    ma.setId(Integer.parseInt(id));
    if (!(simpleCRUD.findMahasiswaById(ma.getId()).equals(""))) {
      simpleCRUD.deleteMahasiswa(ma);
    }
  }

  @RequestMapping(value = "/findByNama", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_XML_VALUE})

  @ResponseBody
  @ApiOperation(value = "cari nama mahasiwa", notes = " blablablablalasjadksahjdhj")
  public GdnRestSingleResponse<MahasiswaDTO> findByNama(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String username) {
    Mahasiswa m = simpleCRUD.findByNama(username);
    MahasiswaDTO md = new MahasiswaDTO(m.getId() + "", m.getNama(), m.getNpm());
    GdnRestSingleResponse<MahasiswaDTO> obj =
        new GdnRestSingleResponse<MahasiswaDTO>(md, requestId);
    return obj;
  }

  @RequestMapping(value = "/findMahasiswaById", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_XML_VALUE})

  @ResponseBody
  @ApiOperation(value = "cari mahasiswa berdasarkan id", notes = "asasadasdasdsad")
  public GdnRestSingleResponse<MahasiswaDTO> findMahasiswaById(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String mahId) {
    Mahasiswa m = simpleCRUD.findMahasiswaById(Integer.parseInt(mahId));
    MahasiswaDTO md = new MahasiswaDTO(m.getId() + "", m.getNama(), m.getNpm());
    GdnRestSingleResponse<MahasiswaDTO> obj =
        new GdnRestSingleResponse<MahasiswaDTO>(md, requestId);
    return obj;
  }

  @RequestMapping(value = "/findMahasiswaDetail", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_XML_VALUE})

  @ResponseBody
  @ApiOperation(value = "cari mahasiswa berdasarkan id yang detail", notes = "asasadasdasdsad")
  public GdnRestSingleResponse<MahasiswaDTO> findMahasiswaDetail(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String mahId) {
    Mahasiswa m = simpleCRUD.findMahasiswaById(Integer.parseInt(mahId));
    MahasiswaDTO md = new MahasiswaDTO(m.getId() + "", m.getNama(), m.getNpm());
    GdnRestSingleResponse<MahasiswaDTO> obj =
        new GdnRestSingleResponse<MahasiswaDTO>(md, requestId);
    return obj;
  }

  @RequestMapping(value = "/getAll", method = RequestMethod.GET,
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

  @RequestMapping(value = "/saveMahasiswa", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_XML_VALUE})

  @ResponseBody
  @ApiOperation(value = "save mahasiswa", notes = "asasadasdasdsad")
  public GdnRestSingleResponse<MahasiswaDTO> saveMahasiswa(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String username, @RequestParam String npm) {
    Mahasiswa m = new Mahasiswa();
    m.setNama(username);
    m.setNpm(npm);
    simpleCRUD.saveMahasiswa(m);
    MahasiswaDTO md = new MahasiswaDTO(simpleCRUD.findByNama(username).getId() + "", username, npm);
    GdnRestSingleResponse<MahasiswaDTO> obj =
        new GdnRestSingleResponse<MahasiswaDTO>(md, requestId);
    return obj;
  }
}
