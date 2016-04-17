package com.ega.dto.request;

import com.gdn.common.web.base.BaseRequest;

public class MataKuliahDTORequest extends BaseRequest {
  private String nama;

  private String kode;

  private String namaDosen;

  public String getKode() {
    return kode;
  }

  public String getNama() {
    return nama;
  }

  public String getNamaDosen() {
    return namaDosen;
  }

  public void setKode(String kode) {
    this.kode = kode;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public void setNamaDosen(String namaDosen) {
    this.namaDosen = namaDosen;
  }

}
