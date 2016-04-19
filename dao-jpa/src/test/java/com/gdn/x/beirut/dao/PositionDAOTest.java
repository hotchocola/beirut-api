package com.gdn.x.beirut.dao;

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
  private PositionDAO positionDAO;

  @Before
  public void initialize(){
    this.position1= new Position();
    this.position2= new Position();
    this.position1.setTitle("Nanami");
    this.position2.setTitle("Budi");
    this.position1.setCreatedBy("Aderai");
    this.positionDAO.save(this.position1);
  }

  @Test
  public void testFindByTitle(){
    assertTrue(this.positionDAO.findByTitle(this.position2.getTitle()).isEmpty());
  }

  @Test
  public void testSave(){
    assertTrue(this.positionDAO.findByTitleContaining(this.position1.getTitle()).size()==1);
    assertTrue(this.positionDAO.findByTitleContaining("Nana").size()==1);
    assertTrue(this.positionDAO.findByTitleContaining(this.position2.getTitle()).isEmpty());
    //LOG.info("position list = {}", this.positionDao.findOne(this.position1.getId()));
  }

}
