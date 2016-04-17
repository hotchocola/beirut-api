package com.ega.dao;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.ega.entities.Mahasiswa;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
public class MahasiswaDaoTest {
  private Mahasiswa maha1;
  @Autowired
  private MahasiswaDao repo;

  @Before
  public void initialize() {
    maha1 = new Mahasiswa();
    maha1.setNama("Kucing");
    maha1.setId("121");
    maha1.setNpm("12232");
    maha1.setStoreId("storeid");
    maha1.setCreatedBy("adm");
    maha1.setCreatedDate(new Date(2016, 04, 14));
    maha1.setMarkForDelete(false);
    maha1.setUpdatedBy("adm");
    maha1.setUpdatedDate(new Date(2016, 04, 14));
    maha1.setVersion(new Long(1));

    this.repo.save(maha1);
  }

  @Test
  public void testFindByNama() {
    Assert.assertTrue(this.repo.findByNama("Kucing").size() == 1);
  }
}
