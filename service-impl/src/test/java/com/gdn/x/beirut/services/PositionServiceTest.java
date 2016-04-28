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
  private PositionServiceImpl service;

  private Position position;
  private final List<Position> pos = new ArrayList<Position>();

  @Test
  public void checkMarkForDelete(){
    List<String> ids = new ArrayList<String>();
    for(int i=0; i<pos.size(); i++){
      ids.add(pos.get(i).getId());
    }
    System.out.println("IDs :" + ids.toString());
    this.service.markForDeletePosition(ids);
    Mockito.verify(this.repository).findByIdAndMarkForDelete(this.position.getId(), false);
  }

  @Test
  public void checkSavePosition(){
    this.service.insertNewPosition(this.position);
    Mockito.verify(this.repository, Mockito.times(1)).save(this.position);
  }

  @Test
  public void checkUpdatePositionTitle(){
    List<Position> positions = new ArrayList<Position>();
    Position posi1 = new Position("12");
    posi1.setTitle("Kamabaka");
    posi1.setId("1");
    positions.add(posi1);
    this.service.updatePositionTitle(posi1.getId(), "Emporio Ivankov");
    Mockito.verify(this.repository).findByIdAndMarkForDelete(this.position.getId(), false);
  }

  @Before
  public void initialize() throws Exception {
    initMocks(this);

    this.position=new Position("12");
    this.position.setTitle("Choa");
    this.position.setId("1");
    pos.add(this.position);
    Mockito.when(this.repository.findByTitleContainingAndMarkForDelete(this.position.getTitle(), false)).thenReturn(pos);
  }
}
