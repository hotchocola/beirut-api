package com.gdn.x.beirut.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name= "Position")
public class Position extends GdnBaseEntity {

  @Column(name= "title")
  private String title;

 // @Column(name="candidatePosition")
 // @OneToMany(mappedBy="position")
  //private final Set<CandidatePosition> candidatePosition= new HashSet<CandidatePosition>();

  public Position(){
    super();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
