/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.ega.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ega Prianto
 */
@Entity
@Table(name = "Mahasiswa")
public class Mahasiswa implements Serializable {

  private static final long serialVersionUID = -8990289988119348524L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "Nama_Mahasiswa")
  private String nama;

  @Column(name = "Npm_Mahasiswa")
  private String npm;

  @Column(name = "MataKuliah_Mahasiswa")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "mahasiswa")
  private Set<MataKuliah> mataKuliah = new HashSet<MataKuliah>();

  public Mahasiswa() {
    // nothing to do here
  }

  public Integer getId() {
    return id;
  }

  public Set<MataKuliah> getMataKuliah() {
    return mataKuliah;
  }

  public String getNama() {
    return nama;
  }

  public String getNpm() {
    return npm;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setMataKuliah(Set<MataKuliah> mataKuliah) {
    this.mataKuliah = mataKuliah;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public void setNpm(String npm) {
    this.npm = npm;
  }
}
