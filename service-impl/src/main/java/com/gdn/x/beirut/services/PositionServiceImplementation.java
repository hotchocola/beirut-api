package com.gdn.x.beirut.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.Position;

@Service(value = "positionService")
@Transactional(readOnly = true)
public class PositionServiceImplementation implements PositionService {

  @Autowired
  private PositionDAO positionDAO;

  @Override
  public List<Position> getAllPosition() {
    return null;
  }

  public PositionDAO getPositionDao(){
    return this.positionDAO;
  }

  @Override
  @Transactional(readOnly = false)
  public void insertNewPosition(Position position) {
    this.getPositionDao().save(position);
  }

  @Override
  @Transactional(readOnly = false)
  public void markForDeletePosition(Position position) {
   List<Position> positionTemp = this.getPositionDao().findByTitleContainingAndMarkForDelete(position.getTitle(), false);
   for(int i=0; i< positionTemp.size(); i++){
     positionTemp.get(i).setMarkForDelete(true);
   }
   this.getPositionDao().save(positionTemp);
  }

  @Override
  @Transactional(readOnly = false)
  public void updatePosition(Position oldPosition, Position newPosition) {
      List<Position> position= this.getPositionDao().findByTitleContainingAndMarkForDelete(oldPosition.getTitle(), false);
      for(int i=0; i< position.size(); i++){
        this.getPositionDao().save(newPosition);
      }
  }

}
