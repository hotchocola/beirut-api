package com.ega.dto.response;

public class MahasiswaDetilDTO extends MahasiswaDTO {
  private MataKuliahDTO[] setMataKuliah;

  public MataKuliahDTO[] getListMataKuliah() {
    return setMataKuliah;
  }

  public void setSetMataKuliah(MataKuliahDTO[] set) {
    this.setMataKuliah = set;
  }



}
