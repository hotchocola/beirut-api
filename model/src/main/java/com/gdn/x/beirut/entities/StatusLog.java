
package com.gdn.x.beirut.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = "StatusLog")
public class StatusLog extends GdnBaseEntity {

  @Column(name = "oldStatus")
  private Status oldStatus;

  @Column(name = "newStatus")
  private Status newStatus;

  @ManyToOne
  @JoinColumn(name = "candidatePosition")
  private CandidatePosition candidatePosition;

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    StatusLog other = (StatusLog) obj;
    if (newStatus != other.newStatus)
      return false;
    if (oldStatus != other.oldStatus)
      return false;
    return true;
  }

  @Column(name = "newStatus")
  @Enumerated(EnumType.STRING)
  private Status getNewStatus() {
    return newStatus;
  }

  @Column(name = "oldStatus")
  @Enumerated(EnumType.STRING)
  private Status getOldStatus() {
    return oldStatus;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((newStatus == null) ? 0 : newStatus.hashCode());
    result = prime * result + ((oldStatus == null) ? 0 : oldStatus.hashCode());
    return result;
  }

  public void setNewStatus(Status newStatus) {
    this.newStatus = newStatus;
  }

  public void setOldStatus(Status oldStatus) {
    this.oldStatus = oldStatus;
  }
}
