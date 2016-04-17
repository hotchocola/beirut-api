package com.ega.dto.request;

import java.util.Set;

import com.gdn.common.web.base.BaseRequest;

public class MahasiswaDTORequest extends BaseRequest {

	private String nama;
	private String npm;
	private Set<MatakuliahDTORequest> matakuliahs;
	
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
	public Set<MatakuliahDTORequest> getMatakuliahs() {
		return matakuliahs;
	}
	public void setMatakuliahs(Set<MatakuliahDTORequest> matakuliahs) {
		this.matakuliahs = matakuliahs;
	}
	
}
