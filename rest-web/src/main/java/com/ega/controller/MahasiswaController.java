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
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/api/mahasiswa/")
@Api(value = "MahasiswaController")
public class MahasiswaController {

	@Autowired
	private SimpleCRUD simpleCRUD;
	
	@RequestMapping(value = "/api/mahasiswa/getAllMahasiswa", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "all mahasiswa value",notes="ambil semua mahasiswa")
	@ResponseBody
	public GdnRestListResponse<MahasiswaDTO> getAll(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=false) String username) {
	
		List<Mahasiswa> temp = simpleCRUD.getAll();
		List<MahasiswaDTO> terserah= new ArrayList<MahasiswaDTO>();
	
		for(int i=0; i<temp.size(); i++){
			terserah.add(new MahasiswaDTO(String.valueOf(temp.get(i).getId()), temp.get(i).getNama(), temp.get(i).getNpm()));
		}

		return new GdnRestListResponse<MahasiswaDTO>(terserah, new PageMetaData(50, 0, temp.size()), requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/findByName", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find mahasiswa by name",notes="cari nama mahasiswa berdasarkan nama")
	@ResponseBody
	public GdnRestSingleResponse<MahasiswaDTO> findByName(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) String username) {
		
	    Mahasiswa temp=simpleCRUD.findByNama(username);
	    MahasiswaDTO mahas = new MahasiswaDTO(String.valueOf(temp.getId()), temp.getNama(), temp.getNpm());
	    
	    return new GdnRestSingleResponse<MahasiswaDTO>(mahas, requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/saveMahasiswa", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "save mahasiswa",notes="menyimpan mahasiswa dengan nama ini.")
	@ResponseBody
	public GdnBaseRestResponse saveMahasiswa(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) String username, @RequestParam(required=true)String npm){
		
		Mahasiswa temp = new Mahasiswa();
		temp.setId(23);
		temp.setNama(username);
		temp.setNpm(npm);
		simpleCRUD.saveMahasiswa(temp);
		return new GdnBaseRestResponse(true);
		
	}
	
	@RequestMapping(value = "/api/mahasiswa/deleteMahasiswa", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "delete mahasiswa",notes="membuang mahasiswa dengan nama ini.")
	@ResponseBody
	public GdnBaseRestResponse deleteMahasiswa(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true)String username, @RequestParam(required=true)String npm){
		
		Mahasiswa temp = new Mahasiswa();
		Mahasiswa x = simpleCRUD.findByNama(username);
		if(!x.equals("")){
			temp.setId(23);
			temp.setNama(username);
			temp.setNpm(npm);
			simpleCRUD.deleteMahasiswa(temp);
			return new GdnBaseRestResponse(true);
		}
		return new GdnBaseRestResponse(false);
		//belum bisa gatau kenapa ga bisa kehapus. 
		// udah nyoba langsung delete ga pake check ada atau engga juga ga bisa.	
	}
	
	@RequestMapping(value = "/api/mahasiswa/findMahasiswaById", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find mahasiswa by ID",notes="cari nama mahasiswa berdasarkan ID")
	@ResponseBody
	public GdnRestSingleResponse<MahasiswaDTO> findMahasiswaById(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) int id) {
		
	    Mahasiswa temp=simpleCRUD.findMahasiswaById(id);
	    MahasiswaDTO mahas = new MahasiswaDTO(String.valueOf(temp.getId()), temp.getNama(), temp.getNpm());
	    
	    return new GdnRestSingleResponse<MahasiswaDTO>(mahas, requestId);
	}
	
}


