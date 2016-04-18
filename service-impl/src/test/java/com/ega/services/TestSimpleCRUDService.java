package com.ega.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.MockitoAnnotations.initMocks;

import com.ega.dao.MahasiswaDao;
import com.ega.entities.Mahasiswa;

public class TestSimpleCRUDService {

	@Mock
	private MahasiswaDao repository;
	
	@InjectMocks
	private SimpleCRUDService service;
	
	private Mahasiswa mahas;
	
	@Before
	public void initialize() throws Exception {
		initMocks(this);
		
		this.mahas = new Mahasiswa();
		mahas.setNama("yuhuuu");
		Mockito.when(this.repository.findOne("1234")).thenReturn(mahas);
		
		Mockito.when(this.repository.save(mahas)).thenReturn(mahas);
	}
	
	@Test
	public void checkTerpanggil(){
		this.service.findMahasiswaById("1234");
		Mockito.verify(this.repository, Mockito.times(1)).findOne("1234"); // harus sesuai sama yang di when
	}
	
	@Test
	public void checkSaveMahasiswa(){
		this.service.saveMahasiswa(this.mahas);
		Mockito.verify(this.repository, Mockito.times(1)).save(this.mahas);
	}
	
}
