package com.gdn.x.beirut.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name= "Position")
public class Position extends GdnBaseEntity {

  @Column(name= "title")
  private String title;

  @Column(name="candidatePosition")
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="position")
  private Set<CandidatePosition> candidatePosition= new HashSet<CandidatePosition>();

  public Position(String STORE_ID){
    super();
  }

  public void addCandidatePosition(CandidatePosition candpos){
    this.candidatePosition.add(candpos);
  }

  public Set<CandidatePosition> getCandidatePosition() {
    return candidatePosition;
  }

  public String getTitle() {
    return title;
  }

  public void setCandidatePosition(Set<CandidatePosition> candpos){
    this.candidatePosition=candpos;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
