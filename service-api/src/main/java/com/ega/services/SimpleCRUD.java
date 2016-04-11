package com.ega.services;

import java.util.List;

import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;

public interface SimpleCRUD {

  List<Mahasiswa> findByName(String name);
  Mahasiswa findMahasiswaById(int id);
  Mahasiswa findMahasiswaDetail(int id);
  void saveMahasiswa(Mahasiswa mahasiswa);
  void deleteMahasiswa(Mahasiswa mahasiswa);
  List<Mahasiswa> getAll();
  void updateMahasiswa(Mahasiswa mahasiswa);
  //List<MataKuliah> findByMahasiswa(int id);
  
  List<MataKuliah> findByNamaMK(String nama);
  MataKuliah findMatakuliahById(int id);
  void saveMatakuliah(MataKuliah matakuliah);
}
