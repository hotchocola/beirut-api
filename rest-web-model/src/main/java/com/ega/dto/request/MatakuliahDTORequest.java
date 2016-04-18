package com.ega.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class MatakuliahDTORequest extends BaseRequest {
	private String nama;
	private String kode;
	private String namaDosen;
	
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public String getKode() {
		return kode;
	}
	public void setKode(String kode) {
		this.kode = kode;
	}
	public String getNamaDosen() {
		return namaDosen;
	}
	public void setNamaDosen(String namaDosen) {
		this.namaDosen = namaDosen;
	}
	
	
}
