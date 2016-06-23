package com.gdn.x.beirut.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.common.base.domainevent.publisher.PublishDomainEvent;
import com.gdn.common.enums.ErrorCategory;
import com.gdn.common.exception.ApplicationException;
import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.domain.event.model.PositionNewInsert;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;

@Service(value = "positionService")
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

  @Autowired
  private PositionDAO positionDAO;


  @Override
  @Deprecated
  public List<Position> getAllPositionByStoreId(String storeId) {
    return positionDAO.findByStoreId(storeId);
  }

  @Override
  public Page<Position> getAllPositionByStoreIdWithPageable(String storeId, Pageable pageable) {
    return positionDAO.findByStoreId(storeId, pageable);
  }

  @Override
  public Position getPosition(String storeId, String positionId) throws Exception {
    Position existingPosition = getPositionDao().findByStoreIdAndId(storeId, positionId);
    if (existingPosition == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
          "no position id = " + positionId);
    }
    return existingPosition;
  }

  @Override
  public List<Position> getPositionByIds(List<String> positionIds) {
    return this.positionDAO.findAll(positionIds);
  }

  @Override
  public List<Position> getPositionByStoreIdAndMarkForDelete(String storeId,
      boolean markForDelete) {
    return this.positionDAO.findByStoreIdAndMarkForDelete(storeId, markForDelete);
  }

  @Override
  public List<Position> getPositionByTitle(String title, String storeId) {
    return positionDAO.findByTitleContainingAndStoreIdAndMarkForDelete(title, storeId, false);
  }

  public PositionDAO getPositionDao() {
    return this.positionDAO;
  }

  @Override
  public Position getPositionDetailByIdAndStoreId(String id, String storeId) throws Exception {
    Position position = this.positionDAO.findByIdAndStoreIdAndMarkForDelete(id, storeId, false);
    if (position == null) {
      throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
          "no such id = " + id + " and storeId = " + storeId);
    }
    Hibernate.initialize(position.getCandidatePositions());

    Set<CandidatePosition> candidatePositions = position.getCandidatePositions();
    for (CandidatePosition candidatePosition : candidatePositions) {
      Hibernate.initialize(candidatePosition.getCandidate());
    }
    return position;
  }

  @Override
  @Transactional(readOnly = false)
  @PublishDomainEvent(publishEventClass = PositionNewInsert.class,
      domainEventName = DomainEventName.POSITION_NEW_INSERT)
  public Position insertNewPosition(Position position) {
    for (CandidatePosition iterable_element : position.getCandidatePositions()) {
      iterable_element.setPosition(position);
    }
    return this.getPositionDao().save(position);

  }

  @Override
  @Transactional(readOnly = false)
  public void markForDeletePosition(String storeId, List<String> ids) throws Exception {
    // System.out.println(ids.toString());
    List<Position> positions = new ArrayList<Position>();
    for (int i = 0; i < ids.size(); i++) {
      Position posi = this.getPositionDao().findOne(ids.get(i));
      if (!posi.getStoreId().equals(storeId)) {
        throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
            "position exist but storeId not match");
      }
      Hibernate.initialize(posi.getCandidatePositions());
      Iterator<CandidatePosition> iterator = posi.getCandidatePositions().iterator();
      while (iterator.hasNext()) {
        CandidatePosition candpos = iterator.next();
        candpos.setMarkForDelete(true);
      }
      posi.setMarkForDelete(true);
      positions.add(posi);
    }
    this.getPositionDao().save(positions);
  }

  @Override
  @Transactional(readOnly = false)
  public boolean updatePositionTitle(String storeId, String id, String title) throws Exception {
    Position posi = this.positionDAO.findOne(id);
    if (posi != null) {
      if (!posi.getStoreId().equals(storeId)) {
        throw new ApplicationException(ErrorCategory.DATA_NOT_FOUND,
            "position exist but storeId not match");
      }
      posi.setTitle(title);
      this.positionDAO.save(posi);
      return true;
    } else {
      return false;
    }
  }
}
