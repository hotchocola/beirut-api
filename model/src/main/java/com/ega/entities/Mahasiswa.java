/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.ega.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

/**
 *
 * @author Ega Prianto
 */
@Entity
@Table(name = "Mahasiswa")
public class Mahasiswa extends GdnBaseEntity {

  private static final long serialVersionUID = -8990289988119348524L;

  private static final String STORE_ID = "1";


  // @GeneratedValue(strategy = GenerationType.AUTO)
  // @GeneratedValue(generator = "system-uuid")
  // @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  // private String id;

  @Column(name = "Nama_Mahasiswa")
  private String nama;

  @Column(name = "Npm_Mahasiswa")
  private String npm;

  @Column(name = "MataKuliah_Mahasiswa")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mahasiswa")
  private Set<MataKuliah> mataKuliahs = new HashSet<MataKuliah>();

  public Mahasiswa() {
    // nothing to do here
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Mahasiswa other = (Mahasiswa) obj;
    if (getId() == null) {
      if (other.getId() != null)
        return false;
    } else if (!getId().equals(other.getId()))
      return false;
    return true;
  }

  public Set<MataKuliah> getMataKuliahs() {
    return mataKuliahs;
  }

  public String getNama() {
    return nama;
  }

  public String getNpm() {
    return npm;
  }

  @Override
  public String getStoreId() {
    return STORE_ID;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
    return result;
  }

  public void setMataKuliah(Set<MataKuliah> mataKuliahs) {
    this.mataKuliahs = mataKuliahs;
  }

  public void setMataKuliahs(Set<MataKuliah> mataKuliahs) {
    this.mataKuliahs = mataKuliahs;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public void setNpm(String npm) {
    this.npm = npm;
  }
}
