package com.ega.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.ega.dao.MahasiswaDao;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;

public class SimpleCRUDServiceTest {
  private static final String NAMA = "Maudy";
  private static final String ID = "182983";
  private static final String NPM = "2392839";
  private static final String NAMAMK = "PROSI";
  private static final String IDMK = "232";
  private static final String NAMADOSEN = "VSM";

  @Mock
  private MahasiswaDao maha;

  @InjectMocks // siapa yang jadi subject mocknya
  private SimpleCRUDService simpleCrud;

  @After
  public void finished() throws Exception {

  }

  @Before
  public void initialize() throws Exception {
    initMocks(this); // kalo udh diimpor gausah pake MockitoAnnotation
  }

  @Test
  public void testSaveMahasiswa() throws Exception {
    Mahasiswa m = new Mahasiswa();
    m.setId(ID);
    m.setNpm(NPM);
    m.setNama(NAMA);
    Set<MataKuliah> mataKuliahs = new HashSet<MataKuliah>();
    mataKuliahs.add(new MataKuliah(NAMAMK, IDMK, NAMADOSEN));
    m.setMataKuliah(mataKuliahs);
    when(this.maha.save(m)).thenReturn(m);
    this.simpleCrud.saveMahasiswa(m);
    verify(this.maha).save(m);
    // assert idnya sama ga
  }
}
