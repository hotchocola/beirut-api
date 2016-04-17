package com.ega.services;

import java.util.List;

import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;

public interface SimpleCRUD {

  Mahasiswa deleteMahasiswaById(String id);

  MataKuliah deleteMataKuliahById(String id);

  Mahasiswa findMahasiswaById(String id);

  List<Mahasiswa> findMahasiswaByNama(String nama);

  Mahasiswa findMahasiswaDetail(String id);

  MataKuliah findMataKuliahById(String id);

  List<MataKuliah> findMataKuliahByNama(String nama);

  List<Mahasiswa> getAllMahasiswa();

  List<MataKuliah> getAllMataKuliah();

  void saveMahasiswa(Mahasiswa mahasiswa);

  void saveMataKuliah(MataKuliah mataKuliah);
}
