package com.ega.dto;

import java.util.HashSet;
import java.util.Set;

import com.gdn.common.web.base.BaseResponse;

public class MahasiswaDTO extends BaseResponse {
	private String primaryKey;
	private String nama;
	private String npm;
	
	
	public MahasiswaDTO(String primaryKey, String nama, String npm) {
		
		this.primaryKey = primaryKey;
		this.nama = nama;
		this.npm = npm;
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
