package com.ega.dto.response;

import com.gdn.common.web.base.BaseResponse;

public class MatakuliahDTOResponse extends BaseResponse {

	private String nama;
	private String kode;
	private String namaDosen;
	
	public MatakuliahDTOResponse(String namaMK, String kodeMK, String namaDosen) {
		super();

		this.nama = namaMK;
		this.kode = kodeMK;
		this.namaDosen = namaDosen;
	}
	
	public MatakuliahDTOResponse(){
		
	}


	public String getNama() {
		return this.nama;
	}

	public void setNama(String namaMK) {
		this.nama = namaMK;
	}

	public String getKode() {
		return this.kode;
	}

	public void setKode(String kodeMK) {
		this.kode = kodeMK;
	}

	public String getNamaDosen() {
		return this.namaDosen;
	}

	public void setNamaDosen(String namaDosen) {
		this.namaDosen = namaDosen;
	}	
	
}
