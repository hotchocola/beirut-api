package com.ega.dto;

import com.gdn.common.web.base.BaseResponse;

public class MatakuliahDTO extends BaseResponse {
	private String primaryKey;
	private String namaMK;
	private String kodeMK;
	private String namaDosen;
	
	public MatakuliahDTO(String primaryKey, String namaMK, String kodeMK, String namaDosen) {
		super();
		this.primaryKey = primaryKey;
		this.namaMK = namaMK;
		this.kodeMK = kodeMK;
		this.namaDosen = namaDosen;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getNamaMK() {
		return namaMK;
	}

	public void setNamaMK(String namaMK) {
		this.namaMK = namaMK;
	}

	public String getKodeMK() {
		return kodeMK;
	}

	public void setKodeMK(String kodeMK) {
		this.kodeMK = kodeMK;
	}

	public String getNamaDosen() {
		return namaDosen;
	}

	public void setNamaDosen(String namaDosen) {
		this.namaDosen = namaDosen;
	}	
	
}
