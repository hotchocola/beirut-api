package com.ega.controller;

import org.dozer.Mapper;

import com.ega.dto.request.MahasiswaDTORequest;
import com.ega.dto.request.MataKuliahDTORequest;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;

public final class StaticHelper {

  public static void forHelper(Mapper dozerMapper, Mahasiswa dest,
      MahasiswaDTORequest newMahasiswa) {
    dest = new Mahasiswa();
    dozerMapper.map(newMahasiswa, dest);
    if (newMahasiswa.getMataKuliahs() != null) {
      for (MataKuliahDTORequest iterable_element : newMahasiswa.getMataKuliahs()) {
        MataKuliah mk = new MataKuliah();
        dozerMapper.map(iterable_element, mk);
        dest.getMataKuliahs().add(mk);
      }
    }
  }



}
