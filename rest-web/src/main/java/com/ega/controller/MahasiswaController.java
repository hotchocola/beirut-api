package com.ega.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dozer.Mapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ega.dto.request.MahasiswaDTORequest;
import com.ega.dto.request.MatakuliahDTORequest;
import com.ega.dto.response.MahasiswaDTOResponse;
import com.ega.dto.response.MatakuliahDTOResponse;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;
import com.ega.services.SimpleCRUD;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.common.web.wrapper.response.PageMetaData;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value = "/api/mahasiswa/")
@Api(value = "MahasiswaController")
public class MahasiswaController {

	@Autowired
	private SimpleCRUD simpleCRUD;
	
	@Autowired
	private Mapper dozerMapper;
	
	@RequestMapping(value = "/api/mahasiswa/getAllMahasiswa", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "all mahasiswa value",notes="ambil semua mahasiswa")
	@ResponseBody
	public GdnRestListResponse<MahasiswaDTOResponse> getAll(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=false) String username) {
	
		List<Mahasiswa> temp = simpleCRUD.getAll();
		List<MahasiswaDTOResponse> terserah= new ArrayList<MahasiswaDTOResponse>();
	
		for(int i=0; i<temp.size(); i++){
			terserah.add(new MahasiswaDTOResponse(temp.get(i).getNama(), temp.get(i).getNpm()));
		}

		return new GdnRestListResponse<MahasiswaDTOResponse>(terserah, new PageMetaData(50, 0, temp.size()), requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/findByName", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find mahasiswa by name",notes="cari nama mahasiswa berdasarkan nama")
	@ResponseBody
	public GdnRestListResponse<MahasiswaDTOResponse> findByName(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) String username) {
		
		List<Mahasiswa> temp = simpleCRUD.findByName(username);
		List<MahasiswaDTOResponse> terserah= new ArrayList<MahasiswaDTOResponse>();
	    
		for(int i=0; i<temp.size(); i++){
			terserah.add(new MahasiswaDTOResponse(temp.get(i).getNama(), temp.get(i).getNpm()));
		}
	    
	    return new GdnRestListResponse<MahasiswaDTOResponse>(terserah, new PageMetaData(50, 0, temp.size()), requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/saveMahasiswa", method= RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "save mahasiswa",notes="menyimpan mahasiswa dengan nama ini.")
	@ResponseBody
	public GdnRestSingleResponse<MahasiswaDTOResponse> saveMahasiswa(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestBody MahasiswaDTORequest mahas){
		
		Mahasiswa temp = new Mahasiswa();
		dozerMapper.map(mahas, temp);
		if(mahas.getMatakuliahs() != null){
			for( MatakuliahDTORequest iterable_element : mahas.getMatakuliahs()){
				MataKuliah matkul= new MataKuliah();
				dozerMapper.map(iterable_element, matkul);
				temp.getMataKuliahs().add(matkul);
				//System.out.println(matkul.toString());
				//System.out.println(temp.getMataKuliahs().toString());
			}
		}
		this.simpleCRUD.saveMahasiswa(temp);
		MahasiswaDTOResponse result = new MahasiswaDTOResponse();
		//for (MataKuliah iterable_element : temp.getMataKuliahs()) {
		//	MatakuliahDTOResponse mk = new MatakuliahDTOResponse();
		//	dozerMapper.map(iterable_element, mk);
		//	result.addMatakuliah(mk);
		//}
		dozerMapper.map(temp, result);
		//System.out.println(temp.getMataKuliahs().toString());
		//System.out.println(result.getMataKuliahs().toString());
		
		return new GdnRestSingleResponse<>(result, requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/deleteMahasiswa", method= RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "delete mahasiswa",notes="membuang mahasiswa dengan nama ini.")
	@ResponseBody
	public GdnRestSingleResponse<MahasiswaDTOResponse> deleteMahasiswa(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) String id){
		
		Mahasiswa temp= simpleCRUD.findMahasiswaById(id);
		MahasiswaDTOResponse res = new MahasiswaDTOResponse();
		dozerMapper.map(temp, res);
		simpleCRUD.deleteMahasiswa(id);
		
		return new GdnRestSingleResponse<MahasiswaDTOResponse>(res, requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/updateMahasiswa", method= RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "update mahasiswa",notes="mengganti mahasiswa.")
	@ResponseBody
	public GdnRestSingleResponse<MahasiswaDTOResponse> updateMahasiswa(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestParam String id, @RequestParam String nama, @RequestParam String npm){
		
		Mahasiswa mahas = simpleCRUD.findMahasiswaById(id);
		mahas.setNama(nama);
		mahas.setNpm(npm);
		simpleCRUD.updateMahasiswa(mahas);
		MahasiswaDTOResponse mres = new MahasiswaDTOResponse();
		dozerMapper.map(mahas, mres);
		
		return new GdnRestSingleResponse<MahasiswaDTOResponse>(mres, requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/findMahasiswaById", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find mahasiswa by ID",notes="cari nama mahasiswa berdasarkan ID")
	@ResponseBody
	public GdnRestSingleResponse<MahasiswaDTOResponse> findMahasiswaById(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true)String id) {
		
	    Mahasiswa temp=simpleCRUD.findMahasiswaById(id);
	    MahasiswaDTOResponse mahas = new MahasiswaDTOResponse(temp.getNama(), temp.getNpm());
	    
	    return new GdnRestSingleResponse<MahasiswaDTOResponse>(mahas, requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/findMahasiswaDetailById", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find detail mahasiswa by ID",notes="cari detail mahasiswa berdasarkan ID")
	@ResponseBody
	public GdnRestSingleResponse<MahasiswaDTOResponse> findMahasiswaDetailById(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true)String id) {
		
	    Mahasiswa temp=simpleCRUD.findMahasiswaDetail(id);
	    MahasiswaDTOResponse mahas = new MahasiswaDTOResponse();
	    mahas.setNama(temp.getNama());
	    mahas.setNpm(temp.getNpm());
	    System.out.println(mahas.toString());
	    Iterator<MataKuliah> iterator = temp.getMataKuliahs().iterator();
	    while(iterator.hasNext()){
	    	MataKuliah res = iterator.next();
	    	System.out.println(res.toString());
	    	MatakuliahDTOResponse result = new MatakuliahDTOResponse(res.getNama(), res.getKode(), res.getNamaDosen());
	    	mahas.addMatakuliah(result);
	    }
	    return new GdnRestSingleResponse<MahasiswaDTOResponse>(mahas, requestId);
	}
}


