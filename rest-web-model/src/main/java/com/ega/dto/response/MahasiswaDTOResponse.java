package com.ega.dto.response;

import java.util.HashSet;

import java.util.Set;

import com.gdn.common.web.base.BaseResponse;

public class MahasiswaDTOResponse extends BaseResponse {
	private String nama;
	private String npm;
	private Set<MatakuliahDTOResponse> matakuliahs;

	public MahasiswaDTOResponse(String nama, String npm) {
		this.nama = nama;
		this.npm = npm;
		this.matakuliahs=new HashSet<MatakuliahDTOResponse>();
	}
	
	public MahasiswaDTOResponse(String nama, String npm, Set<MatakuliahDTOResponse> mataKuliahs) {
		this.nama = nama;
		this.npm = npm;
		this.matakuliahs=mataKuliahs;
	}
	
	public MahasiswaDTOResponse(){
		this.matakuliahs=new HashSet<MatakuliahDTOResponse>();
	}
	
	public void setMatakuliahs(Set<MatakuliahDTOResponse> mataKuliahs){
		this.matakuliahs=mataKuliahs;
	}
	
	public void addMatakuliah(MatakuliahDTOResponse mataKuliahs){
		this.matakuliahs.add(mataKuliahs);	
	}
	
	public Set<MatakuliahDTOResponse> getMataKuliahs(){
		return this.matakuliahs;
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
