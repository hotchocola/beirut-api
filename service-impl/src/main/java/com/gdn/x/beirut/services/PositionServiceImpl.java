package com.gdn.x.beirut.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;

@Service(value = "positionService")
@Transactional(readOnly = true)
public class PositionServiceImpl implements PositionService {

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
    for (CandidatePosition iterable_element : position.getCandidatePosition()) {
      iterable_element.setPosition(position);
    }
    this.getPositionDao().save(position);
  }

  @Override
  @Transactional(readOnly = false)
  public List<Position> markForDeletePosition(List<String> ids) {
    List<Position> positions = new ArrayList<Position>();
    for(int i=0; i< ids.size(); i++){
        Position posi = this.getPositionDao().findByIdAndMarkForDelete(ids.get(i), false);
        if(posi != null){
          posi.setMarkForDelete(true);
          positions.add(posi);
        } else {
          break;
        }
    }
    this.getPositionDao().save(positions);
    return positions;
  }

  @Override
  @Transactional(readOnly = false)
  public void updatePositionTitle(String id, String title) {
    Position posi = this.getPositionDao().findByIdAndMarkForDelete(id, false);
    if(posi!=null){
      posi.setTitle(title);
      this.getPositionDao().save(posi);
    }
  }
}
