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
import com.ega.dto.MatakuliahDTO;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;
import com.ega.services.SimpleCRUD;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/matakuliah/")
@Api(value = "MahatakuliahController")
public class MatakuliahController {

	@Autowired
	private SimpleCRUD simpleCRUD;
	
	@RequestMapping(value = "/api/matakuliah/findByName", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find matakuliah by name.",notes="cari nama matakuliah.")
	@ResponseBody
	public GdnRestListResponse<MatakuliahDTO> findByName(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) String nameMK) {
		
		List<MataKuliah> temp = simpleCRUD.findByNamaMK(nameMK);
		List<MatakuliahDTO> terserah= new ArrayList<MatakuliahDTO>();
	    
		for(int i=0; i<temp.size(); i++){
			terserah.add(new MatakuliahDTO(String.valueOf(temp.get(i).getId()), temp.get(i).getNama(), temp.get(i).getKode(), temp.get(i).getNamaDosen()));
		}
	    
	    return new GdnRestListResponse<MatakuliahDTO>(terserah, new PageMetaData(50, 0, temp.size()), requestId);
	}
	
	@RequestMapping(value = "/api/matakuliah/findMatakuliahById", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find matakuliah by ID",notes="cari nama matakuliah berdasarkan ID")
	@ResponseBody
	public GdnRestSingleResponse<MatakuliahDTO> findMahasiswaById(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) int id) {
		
	    MataKuliah temp=simpleCRUD.findMatakuliahById(id);
	    MatakuliahDTO mahas = new MatakuliahDTO(String.valueOf(temp.getId()), temp.getNama(), temp.getKode(), temp.getNamaDosen());
	    
	    return new GdnRestSingleResponse<MatakuliahDTO>(mahas, requestId);
	}
	
	@RequestMapping(value = "/api/matakuliah/saveMatakuliah", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "save matakuliah",notes="menyimpan matakuliah.")
	@ResponseBody
	public GdnBaseRestResponse saveMatakuliah(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestParam String namaMK, @RequestParam String kodeMK, @RequestParam String namaDosen){
		
		MataKuliah kul = new MataKuliah(namaMK, kodeMK, namaDosen); 
		kul.setId(1000);
		simpleCRUD.saveMatakuliah(kul);
		return new GdnBaseRestResponse(true);
	}
}
