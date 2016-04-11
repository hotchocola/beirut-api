package com.ega.services;

import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ega.dao.MahasiswaDao;
import com.ega.dao.MataKuliahDao;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;
import com.ega.services.SimpleCRUD;

@Service(value = "simpleCRUD")
@Transactional(readOnly = true)
public class SimpleCRUDService implements SimpleCRUD {

  @Autowired
  private MahasiswaDao mahasiswaDao;
  
  @Autowired
  private MataKuliahDao matakuliahDao;

  @Override
  public List<Mahasiswa> findByName(String name) {
    return getMahasiswaDao().findByNamaContaining(name);
  }

  @Override
  public Mahasiswa findMahasiswaById(int id) {
    return getMahasiswaDao().findOne(id);
  }

  @Override
  //  @Transactional(readOnly = false)
  public Mahasiswa findMahasiswaDetail(int id) {
    System.out.println("ambil mahasiswa");
    Mahasiswa mahasiswa = mahasiswaDao.findOne(id);
    System.out.println("ambil relasi mahasiswa");
    Hibernate.initialize(mahasiswa.getMataKuliah());
    return mahasiswa;
  }

  public MahasiswaDao getMahasiswaDao() {
    return mahasiswaDao;
  }
  
  public MataKuliahDao getMataKuliahDao(){
	  return matakuliahDao;
  }

  @Override
  @Transactional(readOnly = false)
  public void saveMahasiswa(Mahasiswa mahasiswa) {
    getMahasiswaDao().save(mahasiswa);
  }
  
  @Override
  @Transactional(readOnly = false)
  public void saveMatakuliah(MataKuliah matakuliah) {
    getMataKuliahDao().save(matakuliah);
  }

  public void setMahasiswaDao(MahasiswaDao mahasiswaDao) {
    this.mahasiswaDao = mahasiswaDao;
  }

  @Override
  public List<Mahasiswa> getAll() {
	return getMahasiswaDao().findAll();
  }

  @Override
  @Transactional(readOnly = false)
  public void deleteMahasiswa(Mahasiswa mahasiswa) {
	 getMahasiswaDao().delete(mahasiswa);
  }

  @Override
  @Transactional(readOnly = false)
  public void updateMahasiswa(Mahasiswa mahasiswa) {
	getMahasiswaDao().save(mahasiswa);
  }

  @Override
  public List<MataKuliah> findByNamaMK(String nama) {
	 return getMataKuliahDao().findByNama(nama);
  }

  @Override
  public MataKuliah findMatakuliahById(int id) {
	return getMataKuliahDao().findOne(id);
  }


  //@Override
 //public List<MataKuliah> findByMahasiswa(int id) {
	//return getMataKuliahDao().findByMahasiswa(id);
  //}
}
