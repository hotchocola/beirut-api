package com.ega.controller;

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

@Controller
@RequestMapping(value = "/api/matakuliah/")
@Api(value = "MatakuliahController")
public class MatakuliahController {

	@Autowired
	private SimpleCRUD simpleCRUD;
	
	@Autowired
	private Mapper dozerMapper;
	
	@RequestMapping(value = "/api/matakuliah/findByName", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find matakuliah by name.",notes="cari nama matakuliah.")
	@ResponseBody
	public GdnRestListResponse<MatakuliahDTOResponse> findByName(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) String nameMK) {
		
		List<MataKuliah> temp = simpleCRUD.findByNamaMK(nameMK);
		List<MatakuliahDTOResponse> terserah= new ArrayList<MatakuliahDTOResponse>();
	    
		for(int i=0; i<temp.size(); i++){
			terserah.add(new MatakuliahDTOResponse(temp.get(i).getNama(), temp.get(i).getKode(), temp.get(i).getNamaDosen()));
		}
	    
	    return new GdnRestListResponse<MatakuliahDTOResponse>(terserah, new PageMetaData(50, 0, temp.size()), requestId);
	}
	
	@RequestMapping(value = "/api/matakuliah/findMatakuliahById", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "find matakuliah by ID",notes="cari nama matakuliah berdasarkan ID")
	@ResponseBody
	public GdnRestSingleResponse<MatakuliahDTOResponse> findMahasiswaById(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId, @RequestParam(required=true) String id) {
		
	    MataKuliah temp=simpleCRUD.findMatakuliahById(id);
	    MatakuliahDTOResponse mahas = new MatakuliahDTOResponse(temp.getNama(), temp.getKode(), temp.getNamaDosen());
	    
	    return new GdnRestSingleResponse<MatakuliahDTOResponse>(mahas, requestId);
	}
	
	@RequestMapping(value = "/api/matakuliah/saveMatakuliah", method= RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "save matakuliah",notes="menyimpan matakuliah.")
	@ResponseBody
	public GdnRestSingleResponse<MatakuliahDTOResponse> saveMatakuliah(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestBody MatakuliahDTORequest matakuliahs){
		
		MataKuliah matkul = new MataKuliah();
		dozerMapper.map(matakuliahs, matkul);
		simpleCRUD.saveMatakuliah(matkul);
		MatakuliahDTOResponse mres = new MatakuliahDTOResponse();
		dozerMapper.map(matkul, mres);
		
		return new GdnRestSingleResponse<>(mres, requestId);
		
	}
	
	@RequestMapping(value = "/api/matakuliah/deleteMatakuliah", method= RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "menghapus matakuliah",notes="menghapus matakuliah.")
	@ResponseBody
	public GdnRestSingleResponse<MatakuliahDTOResponse> deleteMatakuliah(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestParam String id){
		
		MataKuliah temp= simpleCRUD.findMatakuliahById(id);
		MatakuliahDTOResponse mres = new MatakuliahDTOResponse();
		dozerMapper.map(temp, mres);
		simpleCRUD.deleteMatakuliah(temp);
		
		return new GdnRestSingleResponse<>(mres, requestId);
	}
	
	@RequestMapping(value = "/api/matakuliah/updateMatakuliah", method= RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "mengganti matakuliah",notes="mengganti matakuliah.")
	@ResponseBody
	public GdnRestSingleResponse<MatakuliahDTOResponse> deleteMatakuliah(@RequestParam String clientId, @RequestParam String storeId, 
			@RequestParam String requestId, @RequestParam String channelId, @RequestParam String id, @RequestBody MatakuliahDTORequest matakuliah){
		
		MataKuliah mtk = simpleCRUD.findMatakuliahById(id);
		MataKuliah dest = new MataKuliah();
		dozerMapper.map(matakuliah, dest);
		mtk.setNama(dest.getNama());
		mtk.setKode(dest.getKode());
		mtk.setNamaDosen(dest.getNamaDosen());
		MatakuliahDTOResponse mres = new MatakuliahDTOResponse();
		dozerMapper.map(mtk, mres);
		simpleCRUD.updateMatakuliah(mtk);
		
		
		return new GdnRestSingleResponse<>(mres, requestId);
	}
	
	@RequestMapping(value = "/api/mahasiswa/getAllMatakuliah", method= RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@ApiOperation(value = "all matakuliah value",notes="ambil semua matakuliah")
	@ResponseBody
	public GdnRestListResponse<MatakuliahDTOResponse> getAll(@RequestParam String clientId, @RequestParam String storeId, 
		@RequestParam String requestId, @RequestParam String channelId) {
	
		List<MataKuliah> temp = simpleCRUD.getAllMatakuliah();
		List<MatakuliahDTOResponse> terserah= new ArrayList<MatakuliahDTOResponse>();
	
		for(int i=0; i<temp.size(); i++){
			terserah.add(new MatakuliahDTOResponse(temp.get(i).getNama(), temp.get(i).getKode(), temp.get(i).getNamaDosen()));
		}

		return new GdnRestListResponse<MatakuliahDTOResponse>(terserah, new PageMetaData(50, 0, temp.size()), requestId);
	}
}
