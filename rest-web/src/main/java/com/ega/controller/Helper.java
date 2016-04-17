package com.ega.controller;

import org.dozer.Mapper;

import com.ega.dto.request.MahasiswaDTORequest;
import com.ega.dto.request.MatakuliahDTORequest;
import com.ega.entities.Mahasiswa;
import com.ega.entities.MataKuliah;

public final class Helper {
	
	public final static Mahasiswa cariSemuaMatakuliahDariMahasiswa(Mapper dozerMapper, MahasiswaDTORequest mahas){
		Mahasiswa temp = new Mahasiswa();
		dozerMapper.map(mahas, temp);
		if(mahas.getMatakuliahs() != null){
			for( MatakuliahDTORequest iterable_element : mahas.getMatakuliahs()){
				MataKuliah matkul= new MataKuliah();
				dozerMapper.map(iterable_element, matkul);
				temp.getMataKuliahs().add(matkul);
			}
		}
		return temp;
	}
}
