/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Ega Prianto
 */
@Entity
@Table(name = "Mahasiswa")
public class Mahasiswa implements Serializable {

  private static final long serialVersionUID = -8990289988119348524L;

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  private String id;

  @Column(name = "Nama_Mahasiswa")
  private String nama;

  @Column(name = "Npm_Mahasiswa")
  private String npm;

  @Column(name = "MataKuliah_Mahasiswa")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="mahasiswa")
  private Set<MataKuliah> matakuliahs = new HashSet<MataKuliah>();

  public Mahasiswa() {
    // nothing to do here
  }

  public String getId(){
	  return this.id;
  }
  
  public void setId(String id){
	  this.id=id;  
  }
  
  public Set<MataKuliah> getMataKuliahs() {
    return this.matakuliahs;
  }

  public String getNama() {
    return nama;
  }

  public String getNpm() {
    return npm;
  }

  public void setMataKuliah(Set<MataKuliah> mataKuliahs) {
    this.matakuliahs = mataKuliahs;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public void setNpm(String npm) {
    this.npm = npm;
  }
  
  public String toString(){
	  //MataKuliah[] arr = (MataKuliah[]) this.mataKuliah.toArray();
	 // String res="";
	 // for(int i=0; i< this.mataKuliah.size(); i++){
		//  res+=arr[i];
	 // }
	  return this.getNama() + this.getNpm();
  }
}
