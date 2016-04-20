package com.gdn.x.beirut.services;

import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.Position;

public class PositionServiceTest {

  @Mock
  private PositionDAO repository;

  @InjectMocks
  private PositionServiceImplementation service;

  private Position position;

  @Test
  public void checkMarkForDelete(){
    this.service.markForDeletePosition(this.position);
    Mockito.verify(this.repository, Mockito.times(1)).findByTitleContainingAndMarkForDelete(this.position.getTitle(), false);
    List<Position> posi = new ArrayList<Position>();
    posi.add(this.position);
    Mockito.verify(this.repository, Mockito.times(1)).save(posi);
  }

  @Test
  public void checkSavePosition(){
    this.service.insertNewPosition(this.position);
    Mockito.verify(this.repository, Mockito.times(1)).save(this.position);
  }

  @Test
  public void checkUpdatePosition(){
    List<Position> position = new ArrayList<Position>();
    for(int i=0;i<5; i++){
      Position posi = new Position();
      posi.setTitle(i+"a");
      if(i%2 == 0){
        posi.setMarkForDelete(true);
      } else {
        posi.setMarkForDelete(false);
      }
      position.add(posi);
    }
    this.service.updatePosition(position.get(0), position.get(1));
    Mockito.verify(this.repository).findByTitleContainingAndMarkForDelete(position.get(0).getTitle(), false);
  }

  @Before
  public void initialize() throws Exception {
    initMocks(this);

    this.position=new Position();
    this.position.setTitle("Choa");
    List<Position> pos = new ArrayList<Position>();
    pos.add(this.position);
    Mockito.when(this.repository.findByTitleContainingAndMarkForDelete(this.position.getTitle(), false)).thenReturn(pos);
  }
}
