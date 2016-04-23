//package com.gdn.x.beirut.entities;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.Table;
//
//import com.gdn.common.base.entity.GdnBaseEntity;
//
//@Entity
//@Table(name = "statusLog")
//public class StatusLog extends GdnBaseEntity {
//
//  // @Column(name = "newStatus")
//  // private enum newStatus {
//  // OPEN, ONHOLD, DECLINED, WITHDRAWL, JOIN
//  // }
//
//  private enum newStatus {
//    OPEN, ONHOLD, DECLINED, WITHDRAWL, JOIN
//  };
//
//  private enum oldStatus {
//    OPEN, ONHOLD, DECLINED, WITHDRAWL, JOIN
//  };
//
//  private oldStatus oldStatus;
//  private newStatus newStatus;
//
//  //@ManyToOne
//  //@JoinColumn(name = "candidatePosition")
//  //private CandidatePosition candidatePosition;
//
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj)
//      return true;
//    if (!super.equals(obj))
//      return false;
//    if (getClass() != obj.getClass())
//      return false;
//    StatusLog other = (StatusLog) obj;
//    if (newStatus != other.newStatus)
//      return false;
//    if (oldStatus != other.oldStatus)
//      return false;
//    return true;
//  }
//
//  @Column(name = "newStatus")
//  @Enumerated(EnumType.STRING)
//  private newStatus getNewStatus() {
//    return newStatus;
//  }
//
//  @Column(name = "oldStatus")
//  @Enumerated(EnumType.STRING)
//  private oldStatus getOldStatus() {
//    return oldStatus;
//  }
//
//  @Override
//  public int hashCode() {
//    final int prime = 31;
//    int result = super.hashCode();
//    result = prime * result + ((newStatus == null) ? 0 : newStatus.hashCode());
//    result = prime * result + ((oldStatus == null) ? 0 : oldStatus.hashCode());
//    return result;
//  }
//
//  public void setNewStatus(newStatus newStatus) {
//    this.newStatus = newStatus;
//  }
//
//  public void setOldStatus(oldStatus oldStatus) {
//    this.oldStatus = oldStatus;
//  }
//}
