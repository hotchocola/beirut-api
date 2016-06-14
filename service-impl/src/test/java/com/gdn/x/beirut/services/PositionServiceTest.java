package com.gdn.x.beirut.services;

import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

  @PersistenceContext
  private EntityManager em;

  private Position position;
  private final List<Position> pos = new ArrayList<Position>();



  // @Test
  // @Ignore
  // public void checkMarkForDelete() {
  // List<String> ids = new ArrayList<String>();
  // for (int i = 0; i < pos.size(); i++) {
  // ids.add(pos.get(i).getId());
  // }
  // this.service.markForDeletePosition(ids);
  // Mockito.verify(this.repository).findByIdAndMarkForDelete(this.position.getId(), false);
  // }


  @Test
  public void checkUpdatePositionTitle() {
    this.service.updatePositionTitle(this.position.getId(), "Emporio Ivankov");
    verify(this.repository).findOne(this.position.getId());
  }

  @Test
  public void getAllPosition() {
    this.service.getAllPosition();
    verify(this.repository, Mockito.times(1)).findAll();
  }

  @Test
  public void getPositionByTitle() {
    this.service.getPositionByTitle("Cho", "Store");
    verify(this.repository).findByTitleContainingAndStoreIdAndMarkForDelete("Cho", "Store", false);
  }

  @Before
  public void initialize() throws Exception {
    initMocks(this);

    this.position = new Position();
    this.position.setTitle("Choa");
    this.position.setId("122");
    this.position.setStoreId("Store");
    pos.add(this.position);
    this.repository.save(this.position);
    Mockito.when(this.repository
        .findByTitleContainingAndStoreIdAndMarkForDelete(this.position.getTitle(), "Store", false))
        .thenReturn(pos);
    Mockito.when(this.repository.save(this.position)).thenReturn(this.position);
    List<String> aa = new ArrayList<String>();
    aa.add("1");
  }

}
