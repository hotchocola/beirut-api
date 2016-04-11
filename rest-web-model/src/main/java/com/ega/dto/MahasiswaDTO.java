package com.ega.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gdn.common.web.base.BaseResponse;

public class MahasiswaDTO extends BaseResponse {
	private String primaryKey;
	private String nama;
	private String npm;
	private Set<MatakuliahDTO> matkul;

	public MahasiswaDTO(String primaryKey, String nama, String npm) {
		this.primaryKey = primaryKey;
		this.nama = nama;
		this.npm = npm;
	}
	
	public MahasiswaDTO(String primaryKey, String nama, String npm, Set<MatakuliahDTO> matkul) {
		this.primaryKey = primaryKey;
		this.nama = nama;
		this.npm = npm;
		this.matkul=matkul;
	}
	
	public MahasiswaDTO(){
		this.matkul=new HashSet<MatakuliahDTO>();
	}
	
	public void setMatakuliah(Set<MatakuliahDTO> matakuliah){
		this.matkul=matakuliah;
	}
	
	public void addMatakuliah(MatakuliahDTO matkuliah){
		this.matkul.add(matkuliah);	
	}
	
	public Set<MatakuliahDTO> getMataKuliah(){
		return this.matkul;
	}
	
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getNpm() {
		return npm;
	}
	public void setNpm(String npm) {
		this.npm = npm;
	}
}
