/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ega.dao;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.ega.entities.Mahasiswa;


/**
 *
 * @author Ega Prianto
 */

public interface MahasiswaDao extends JpaRepository<Mahasiswa, String>  {

  List<Mahasiswa> findByNamaContaining(String name);

}
