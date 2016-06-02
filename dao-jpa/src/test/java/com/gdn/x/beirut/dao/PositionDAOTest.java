package com.gdn.x.beirut.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.gdn.x.beirut.entities.Position;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = {TestConfig.class})
@TestExecutionListeners (listeners= {DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional(readOnly = false)
public class PositionDAOTest {
  //private static final Logger LOG=LoggerFactory.getLogger(TestPositionDao.class);
  private Position position1;

  private Position position2;

  @Autowired
  private PositionDAO positionDao;

  @Test
  public void findById(){
    this.position1.setMarkForDelete(true);
    assertEquals(this.positionDao.findByIdAndMarkForDelete(this.position1.getId(), false), null);
  }

  @Before
  public void initialize(){
    this.position1= new Position();
    this.position2= new Position();
    this.position1.setTitle("Nanami");
    this.position2.setTitle("Budi");
    this.position1.setCreatedBy("Aderai");
    this.positionDao.save(this.position1);
  }

  @Test
  public void testFindByMarkForDelete(){
    assertTrue(this.positionDao.findByMarkForDelete(true).isEmpty());
  }

  @Test
  public void testFindByTitle(){
    assertTrue(this.positionDao.findByTitleContainingAndMarkForDelete(this.position2.getTitle(), false).isEmpty());
  }

  @Test
  public void testSave(){
    System.out.println("aaaa" + this.position1.isMarkForDelete());
    System.out.println("bbbb" + this.position2.isMarkForDelete());
    assertTrue(this.positionDao.findByTitleContainingAndMarkForDelete(this.position1.getTitle(), false).size()==1);
    assertTrue(this.positionDao.findByTitleContainingAndMarkForDelete("Nana", false).size()==1);
    assertTrue(this.positionDao.findByTitleContainingAndMarkForDelete(this.position2.getTitle(), false).isEmpty());
    //LOG.info("position list = {}", this.positionDao.findOne(this.position1.getId()));
  }
}
