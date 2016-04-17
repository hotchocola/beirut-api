package com.ega.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ega.dao.MahasiswaDao;
import com.ega.dao.MataKuliahDao;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;

@Service(value = "simpleCRUD")
@Transactional(readOnly = true)
public class SimpleCRUDService implements SimpleCRUD {

  @Autowired
  private MahasiswaDao mahasiswaDao;

  @Autowired
  private MataKuliahDao mataKuliahDao;

  @Override
  @Transactional(readOnly = false)
  public Mahasiswa deleteMahasiswaById(String id) {
    Mahasiswa temp = mahasiswaDao.findOne(id);
    this.mahasiswaDao.delete(temp);
    return temp;
  }

  @Override
  @Transactional(readOnly = false)
  public MataKuliah deleteMataKuliahById(String id) {
    MataKuliah temp = mataKuliahDao.findOne(id);
    this.mataKuliahDao.delete(temp);
    return temp;
  }

  @Override
  public Mahasiswa findMahasiswaById(String id) {
    return getMahasiswaDao().findOne(id);
  }

  @Override
  public List<Mahasiswa> findMahasiswaByNama(String nama) {
    return getMahasiswaDao().findByNama(nama);
  }

  @Override
  // @Transactional(readOnly = false)
  public Mahasiswa findMahasiswaDetail(String id) {
    // System.out.println("ambil mahasiswa");
    Mahasiswa mahasiswa = mahasiswaDao.findOne(id);
    // System.out.println("ambil relasi mahasiswa");
    Hibernate.initialize(mahasiswa.getMataKuliahs());
    return mahasiswa;
  }

  @Override
  public MataKuliah findMataKuliahById(String id) {
    return mataKuliahDao.findOne(id);
  }

  @Override
  public List<MataKuliah> findMataKuliahByNama(String nama) {
    return mataKuliahDao.findByNama(nama);
  }

  @Override
  public List<Mahasiswa> getAllMahasiswa() {
    return this.mahasiswaDao.findAll();
  }

  @Override
  public List<MataKuliah> getAllMataKuliah() {
    return (List<MataKuliah>) mataKuliahDao.findAll();
  }

  public MahasiswaDao getMahasiswaDao() {
    return mahasiswaDao;
  }

  @Override
  @Transactional(readOnly = false)
  public void saveMahasiswa(Mahasiswa mahasiswa) {
    if (mahasiswa.getMataKuliahs() != null) {
      for (MataKuliah iterable_element : mahasiswa.getMataKuliahs()) {
        iterable_element.setMahasiswa(mahasiswa);
      }
    }
    getMahasiswaDao().save(mahasiswa);
  }


  @Override
  public void saveMataKuliah(MataKuliah mataKuliah) {
    this.mataKuliahDao.save(mataKuliah);

  }

  public void setMahasiswaDao(MahasiswaDao mahasiswaDao) {
    this.mahasiswaDao = mahasiswaDao;
  }

}
