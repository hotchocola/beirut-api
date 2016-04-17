/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.ega.entities;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Ega Prianto
 */
@Entity
@Table(name = "MataKuliah")
public class MataKuliah implements Serializable {

  private static final long serialVersionUID = -1311121582151898747L;

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  // @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ID_MataKuliah")
  private String id;

  @Column(name = "Nama_MataKuliah")
  private String nama;

  @Column(name = "Kode_MataKuliah")
  private String kode;

  @Column(name = "NamaDosen_MataKuliah")
  private String namaDosen;

  @ManyToOne
  @JoinColumn(name = "mahasiswa_id")
  private Mahasiswa mahasiswa;

  public MataKuliah() {
    // nothing todo here
  }

  public MataKuliah(String nama, String kode, String namaDosen) {
    this.nama = nama;
    this.kode = kode;
    this.namaDosen = namaDosen;
  }

  public String getId() {
    return id;
  }

  public String getKode() {
    return kode;
  }

  public Mahasiswa getMahasiswa() {
    return mahasiswa;
  }

  public String getNama() {
    return nama;
  }

  public String getNamaDosen() {
    return namaDosen;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setKode(String kode) {
    this.kode = kode;
  }

  public void setMahasiswa(Mahasiswa mahasiswa) {
    this.mahasiswa = mahasiswa;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public void setNamaDosen(String namaDosen) {
    this.namaDosen = namaDosen;
  }

}
