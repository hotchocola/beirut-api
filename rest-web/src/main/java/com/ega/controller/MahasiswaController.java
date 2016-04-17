package com.ega.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ega.dto.request.MahasiswaDTORequest;
import com.ega.dto.response.MahasiswaDTO;
import com.ega.dto.response.MahasiswaDetilDTO;
import com.ega.dto.response.MataKuliahDTO;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;
import com.ega.services.SimpleCRUD;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/mahasiswa")
@Api(value = "MahasiswaController", description = "Percobaan pertama, pembelajaran pertama")
public class MahasiswaController {

  @Autowired
  private SimpleCRUD simpleCRUD;
  @Autowired
  private Mapper dozerMapper;
  // private ObjectMapper mapper;

  @RequestMapping(value = "/deleteMahasiswa", method = RequestMethod.POST)
  @ApiOperation(value = "delete mahasiswa dengan id value",
      notes = "param id adalah id mahasiswa yang ingin di ganti, param mahasiswaIn adalah data mahasiswa yang baru")
  @ResponseBody
  public GdnRestSingleResponse<MahasiswaDTO> deleteMahasiswaById(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String username, @RequestParam String id) {
    final Mahasiswa deleted = this.simpleCRUD.deleteMahasiswaById(id);
    final MahasiswaDTO deletedMahasiswaDTO = new MahasiswaDTO();
    deletedMahasiswaDTO.setNama(deleted.getNama());
    deletedMahasiswaDTO.setNpm(deleted.getNpm());
    final GdnRestSingleResponse<MahasiswaDTO> gdnDeletedMahasiswa =
        new GdnRestSingleResponse<>(deletedMahasiswaDTO, requestId);
    return gdnDeletedMahasiswa;
  }

  @RequestMapping(value = "/findMahasiswaById", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Ambil 1 mahasiswa sesuai id", notes = "ga detil")
  @ResponseBody
  public GdnRestSingleResponse<MahasiswaDTO> findMahasiswaById(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String username, @RequestParam String id) {
    final Mahasiswa mahasiswa = simpleCRUD.findMahasiswaById(id);
    final MahasiswaDTO newDTO = new MahasiswaDTO();
    newDTO.setPrimaryKey(mahasiswa.getId() + "");
    newDTO.setNama(mahasiswa.getNama());
    newDTO.setNpm(mahasiswa.getNpm());
    return new GdnRestSingleResponse<MahasiswaDTO>(newDTO, requestId);
  }

  @RequestMapping(value = "/findMahasiswaByNama", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Ambil 1 mahasiswa sesuai nama", notes = "ga detil")
  @ResponseBody
  public GdnRestListResponse<MahasiswaDTO> findMahasiswaByNama(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String username, @RequestBody String nama) {
    final List<Mahasiswa> mahasiswa = simpleCRUD.findMahasiswaByNama(nama);
    final List<MahasiswaDTO> mahasiswaDTO = new ArrayList<>();
    for (Mahasiswa mahasiswa2 : mahasiswa) {
      mahasiswaDTO.add(MahasiswaToDTOConvert(mahasiswa2));
    }
    return new GdnRestListResponse<>(mahasiswaDTO, new PageMetaData(50, 0, mahasiswaDTO.size()),
        requestId);
  }

  @RequestMapping(value = "/findMahasiswaDetailById", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Ambil 1 mahasiswa sesuai id", notes = "detil")
  @ResponseBody
  public GdnRestSingleResponse<MahasiswaDetilDTO> findMahasiswaDetail(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String username, @RequestParam(required = true) String id) {
    Mahasiswa mahasiswa = simpleCRUD.findMahasiswaDetail(id);
    MahasiswaDetilDTO newDTO = MahasiswaToDetilDTOConvert(mahasiswa);
    return new GdnRestSingleResponse<MahasiswaDetilDTO>(newDTO, requestId);
  }

  @RequestMapping(value = "/getAllMahasiswa", method = RequestMethod.GET,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "Ambil semua mahasiswa", notes = "ambil semua mahasiswa yang ada")
  @ResponseBody
  public GdnRestListResponse<MahasiswaDTO> getAllMahasiswa(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String username,
      @RequestParam String name, @RequestParam String requestId) {
    final List<Mahasiswa> temp = simpleCRUD.getAllMahasiswa();
    final List<MahasiswaDTO> listDTO = new ArrayList<MahasiswaDTO>();
    for (final Mahasiswa mahasiswa : temp) {
      final MahasiswaDTO newDTO = new MahasiswaDTO();
      newDTO.setPrimaryKey(mahasiswa.getId() + "");
      newDTO.setNama(mahasiswa.getNama());
      newDTO.setNpm(mahasiswa.getNpm());
      listDTO.add(newDTO);
    }
    return new GdnRestListResponse<MahasiswaDTO>(listDTO, new PageMetaData(50, 0, temp.size()),
        requestId);
  }


  public Mapper getDozerMapper() {
    return dozerMapper;
  }

  private MahasiswaDetilDTO MahasiswaToDetilDTOConvert(Mahasiswa in) {
    MahasiswaDetilDTO res = new MahasiswaDetilDTO();
    res.setNama(in.getNama());
    res.setNpm(in.getNpm());
    Set<MataKuliah> mahasiswaMK = in.getMataKuliahs();
    MataKuliahDTO[] listMK = new MataKuliahDTO[mahasiswaMK.size()];
    int counter = 0;
    for (MataKuliah mataKuliah : mahasiswaMK) {
      listMK[counter] = MKToDTOConvert(mataKuliah);
      System.out.println(listMK[counter]);
      counter++;
    }
    res.setSetMataKuliah(listMK);
    return res;
  }

  private MahasiswaDTO MahasiswaToDTOConvert(Mahasiswa in) {
    MahasiswaDTO res = new MahasiswaDTO();
    res.setNama(in.getNama());
    res.setNpm(in.getNpm());
    return res;
  }

  private MataKuliahDTO MKToDTOConvert(MataKuliah in) {
    MataKuliahDTO res = new MataKuliahDTO();
    res.setKode(in.getKode());
    res.setNama(in.getNama());
    res.setNamaDosen(in.getNamaDosen());
    return res;
  }

  @RequestMapping(value = "/saveMahasiswa", method = RequestMethod.POST,
      produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  @ApiOperation(value = "simpan 1 mahasiswa", notes = "")
  @ResponseBody
  public GdnRestSingleResponse<MahasiswaDTO> saveMahasiswa(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String username, @RequestBody MahasiswaDTORequest newMahasiswa) {
    Mahasiswa dest = new Mahasiswa();
    StaticHelper.forHelper(dozerMapper, dest, newMahasiswa);
    this.simpleCRUD.saveMahasiswa(dest);
    MahasiswaDTO resDTO = new MahasiswaDTO();
    dozerMapper.map(dest, resDTO);
    return new GdnRestSingleResponse<>(resDTO, requestId);
  }

  public void setDozerMapper(Mapper dozerMapper) {
    this.dozerMapper = dozerMapper;
  }

  @RequestMapping(value = "/updatingMahasiswa", method = RequestMethod.POST,
      produces = {MediaType.APPLICATION_XML_VALUE},
      consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "update mahasiswa dengan id value",
      notes = "param id adalah id mahasiswa yang ingin di ganti, param mahasiswaIn adalah data mahasiswa yang baru")
  @ResponseBody
  public GdnRestSingleResponse<MahasiswaDTO> updateMahasiswa(@RequestParam String storeId,
      @RequestParam String channelId, @RequestParam String clientId, @RequestParam String requestId,
      @RequestParam String username, @RequestParam String id, @RequestBody Mahasiswa mahasiswaIn) {
    final Mahasiswa mahasiswa = this.simpleCRUD.findMahasiswaById(id);
    mahasiswa.setNama(mahasiswaIn.getNama());
    mahasiswa.setNpm(mahasiswaIn.getNpm());
    this.simpleCRUD.saveMahasiswa(mahasiswa);
    final MahasiswaDTO updatedMahasiswaDTO = new MahasiswaDTO();
    updatedMahasiswaDTO.setNama(mahasiswa.getNama());
    updatedMahasiswaDTO.setNpm(mahasiswa.getNpm());
    final GdnRestSingleResponse<MahasiswaDTO> gdnUpdatedMahasiswa =
        new GdnRestSingleResponse<>(updatedMahasiswaDTO, requestId);
    return gdnUpdatedMahasiswa;
  }
}
