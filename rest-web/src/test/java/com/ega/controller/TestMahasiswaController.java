package com.ega.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.HashSet;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ega.dto.request.MahasiswaDTORequest;
import com.ega.dto.request.MatakuliahDTORequest;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;
import com.ega.services.SimpleCRUD;
import com.ega.services.SimpleCRUDService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMahasiswaController {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final String NAMA = "Maudy";

	  private static final String ID = "182983";
	  private static final String NPM = "2392839";
	  private static final String NAMAMK = "PROSI";
	  private static final String IDMK = "232";
	  private static final String NAMADOSEN = "VSM";
	  private static final String STORE_ID = "storeId";
	  private static final String CLIENT_ID = "clientId";
	  private static final String CHANNEL_ID = "channelId";
	  private static final String REQUEST_ID = "requestId";
	  private static final String USERNAME = "username";
	  private MockMvc mockMVC;

	  private final Mapper dm = new DozerBeanMapper();
	@Mock
	private SimpleCRUD service;

	@InjectMocks
	private MahasiswaController controller;

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		this.controller.setDozerMapper(dm);
		this.mockMVC= standaloneSetup(this.controller).build();
		this.service=new SimpleCRUDService();
	}

	@Test
	public void testFindMahasiswaByID() throws Exception{
	  MahasiswaDTORequest request = new MahasiswaDTORequest();
	  Mahasiswa m = new Mahasiswa();
      m.setId(ID);
      m.setNpm(NPM);
      m.setNama(NAMA);
      Set<MataKuliah> mataKuliahs = new HashSet<MataKuliah>();
      mataKuliahs.add(new MataKuliah(NAMAMK, IDMK, NAMADOSEN));
      m.setMataKuliah(mataKuliahs);

      request.setNama(NAMA);
      request.setNpm(NPM);
      Set<MatakuliahDTORequest> mks = new HashSet<MatakuliahDTORequest>();
      MatakuliahDTORequest mk = new MatakuliahDTORequest();
      mk.setKode(IDMK);
      mk.setNama(NAMAMK);
      mk.setNamaDosen(NAMADOSEN);
      request.setMatakuliahs(mks);

      Mockito.when(this.service.findMahasiswaById(ID)).thenReturn(m);
      this.mockMVC
      .perform(get("/api/mahasiswa/findMahasiswaById").contentType(MediaType.APPLICATION_JSON)
          .content(TestMahasiswaController.OBJECT_MAPPER.writeValueAsString(request))
          .accept(MediaType.APPLICATION_JSON).param("storeId", TestMahasiswaController.STORE_ID)
          .param("channelId", TestMahasiswaController.CHANNEL_ID)
          .param("clientId", TestMahasiswaController.CLIENT_ID)
          .param("requestId", TestMahasiswaController.REQUEST_ID)
          .param("id", TestMahasiswaController.ID)
          .param("username", TestMahasiswaController.USERNAME))
      .andExpect(status().isOk());
      Mockito.verify(this.service, Mockito.times(1)).findMahasiswaById(ID);
	}

	@Test
	  public void testSaveMahasiswaController() throws Exception {
	    MahasiswaDTORequest request = new MahasiswaDTORequest();
	    Mahasiswa m = new Mahasiswa();
	    m.setId(ID);
	    m.setNpm(NPM);
	    m.setNama(NAMA);
	    Set<MataKuliah> mataKuliahs = new HashSet<MataKuliah>();
	    mataKuliahs.add(new MataKuliah(NAMAMK, IDMK, NAMADOSEN));
	    m.setMataKuliah(mataKuliahs);

	    request.setNama(NAMA);
	    request.setNpm(NPM);
	    Set<MatakuliahDTORequest> mks = new HashSet<MatakuliahDTORequest>();
	    MatakuliahDTORequest mk = new MatakuliahDTORequest();
	    mk.setKode(IDMK);
	    mk.setNama(NAMAMK);
	    mk.setNamaDosen(NAMADOSEN);
	    request.setMatakuliahs(mks);


	    Mockito.doNothing().when(this.service).saveMahasiswa(Mockito.any(Mahasiswa.class));
	    this.mockMVC
	        .perform(post("/api/mahasiswa/saveMahasiswa").contentType(MediaType.APPLICATION_JSON)
	            .content(TestMahasiswaController.OBJECT_MAPPER.writeValueAsString(request))
	            .accept(MediaType.APPLICATION_JSON).param("storeId", TestMahasiswaController.STORE_ID)
	            .param("channelId", TestMahasiswaController.CHANNEL_ID)
	            .param("clientId", TestMahasiswaController.CLIENT_ID)
	            .param("requestId", TestMahasiswaController.REQUEST_ID)
	            .param("username", TestMahasiswaController.USERNAME))
	        .andExpect(status().isOk());
	    Mockito.verify(this.service, Mockito.times(1)).saveMahasiswa(Mockito.any(Mahasiswa.class));
	  }
}
