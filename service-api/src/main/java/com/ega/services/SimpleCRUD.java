package com.ega.services;

import java.util.List;

import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;

public interface SimpleCRUD {

  List<Mahasiswa> findByName(String name);
  Mahasiswa findMahasiswaById(String id);
  Mahasiswa findMahasiswaDetail(String id);
  void saveMahasiswa(Mahasiswa mahasiswa);
  void deleteMahasiswa(String id);
  List<Mahasiswa> getAll();
  void updateMahasiswa(Mahasiswa mahasiswa);
  //List<MataKuliah> findByMahasiswa(int id);
  
  List<MataKuliah> findByNamaMK(String nama);
  MataKuliah findMatakuliahById(String id);
  void saveMatakuliah(MataKuliah matakuliah);
  void deleteMatakuliah(MataKuliah matakuliah);
  void updateMatakuliah(MataKuliah matakuliah);
  List<MataKuliah> getAllMatakuliah();
}
