package com.ega.dao;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.ega.entities.Mahasiswa;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {TestConfig.class})
@TestExecutionListeners (listeners= {DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional(readOnly = false)
public class TestMahasiswaDao {

	private Mahasiswa mahasiswa;
	private Mahasiswa mahasiswa2;
	private static final Logger LOG=LoggerFactory.getLogger(TestMahasiswaDao.class);
	
	@Autowired
	private MahasiswaDao repository;
	
	@Before
	public void initialize(){
		this.mahasiswa=new Mahasiswa();
		this.mahasiswa.setNama("Budi");
		this.mahasiswa2=new Mahasiswa();
		this.mahasiswa2.setNama("Ega");
		this.repository.save(this.mahasiswa);
	}
	
	@Test
	public void checkAvailability(){
		//assertTrue(this.repository.findByNamaContaining(this.mahasiswa.getNama()).isEmpty());
		LOG.info("mahasiswa2 nama list = {}", repository.findByNamaContaining(this.mahasiswa2.getNama()));
		assertTrue(this.repository.findByNamaContaining(this.mahasiswa2.getNama()).isEmpty());
	}
	
}
