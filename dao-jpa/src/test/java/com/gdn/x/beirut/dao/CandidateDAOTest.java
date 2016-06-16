package com.gdn.x.beirut.dao;

import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
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

import com.gdn.common.web.param.PageableHelper;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional(readOnly = false)
public class CandidateDAOTest {

  private static final String ID = "id_ini";

  private static final String STORE_ID = "store_id";

  @Autowired
  private CandidateDAO candidateDAO;

  @Before
  public void initialize() {
    for (int i = 0; i < 10; i++) {
      Candidate newCandidate = new Candidate();
      newCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      newCandidate.setFirstName("Ega");
      newCandidate.setLastName("Prianto");
      newCandidate.setPhoneNumber("123456789" + i);
      newCandidate.setStoreId(STORE_ID);
      CandidateDetail candDetail = new CandidateDetail();
      candDetail.setContent(("ini PDF" + i).getBytes());
      newCandidate.setCandidateDetail(candDetail);
      candDetail.setCandidate(newCandidate);
      this.candidateDAO.save(newCandidate);
    }

    Candidate newCandidate = new Candidate();
    newCandidate.setEmailAddress("egaprianto@asd.com");
    newCandidate.setFirstName("Ega");
    newCandidate.setLastName("Prianto");
    newCandidate.setPhoneNumber("1234567890");
    CandidateDetail candDetail = new CandidateDetail();
    candDetail.setContent(("ini PDF").getBytes());
    candDetail.setCandidate(newCandidate);

    newCandidate.setCandidateDetail(candDetail);
    this.candidateDAO.save(newCandidate);
  }



  @Test
  public void testFindByCreatedDateBetween() {
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);
    List<Candidate> res = this.candidateDAO.findByCreatedDateBetweenAndStoreId(start.getTime(),
        end.getTime(), STORE_ID);
    for (Candidate candidate : res) {
      Assert.assertTrue(start.getTime().getTime() <= candidate.getCreatedDate().getTime()
          && end.getTime().getTime() >= candidate.getCreatedDate().getTime());
    }
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByCreatedDateBetweenNoResult() {
    GregorianCalendar start = new GregorianCalendar(1900, 1, 1);
    GregorianCalendar end = new GregorianCalendar(1900, 6, 1);
    List<Candidate> res = this.candidateDAO.findByCreatedDateBetweenAndStoreId(start.getTime(),
        end.getTime(), STORE_ID);
    res.get(0);
  }

  @Test
  public void testFindByEmailAddressAndStoreId() {
    List<Candidate> res =
        this.candidateDAO.findByEmailAddressAndStoreId("egaprianto1@asd.com", STORE_ID);
    Assert.assertNotNull(res.get(0).getFirstName());
    Assert.assertNotNull(res.get(0).getEmailAddress());
    Assert.assertNotNull(res.get(0).getLastName());
    Assert.assertNotNull(res.get(0).getPhoneNumber());
    Assert.assertNotNull(res.get(0).getCandidateDetail());
    Assert.assertTrue(new String(res.get(0).getCandidateDetail().getContent()).equals("ini PDF1"));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByEmailAddressAndStoreIdOnlyOneResult() {
    List<Candidate> res =
        this.candidateDAO.findByEmailAddressAndStoreId("egaprianto1@asd.com", STORE_ID);
    res.get(1);
  }


  @Test
  public void testFindByFirstName() {
    List<Candidate> res = this.candidateDAO.findByFirstName("Ega");
    Assert.assertNotNull(res.get(0).getFirstName());
    Assert.assertNotNull(res.get(0).getEmailAddress());
    Assert.assertNotNull(res.get(0).getLastName());
    Assert.assertNotNull(res.get(0).getPhoneNumber());
    Assert.assertNotNull(res.get(0).getCandidateDetail());
    Assert.assertFalse(new String(res.get(0).getCandidateDetail().getContent())
        .equals(new String(res.get(1).getCandidateDetail().getContent())));
    Assert.assertTrue(res.get(0).getFirstName().equals("Ega"));
    Assert.assertTrue(res.get(0).getFirstName().equals(res.get(1).getFirstName()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByFirstNameNoResult() {
    List<Candidate> res = this.candidateDAO.findByFirstName("asd");
    res.get(0);
  }

  @Test
  public void testFindByLastName() {
    List<Candidate> res = this.candidateDAO.findByLastName("Prianto");
    Assert.assertNotNull(res.get(0).getFirstName());
    Assert.assertNotNull(res.get(0).getEmailAddress());
    Assert.assertNotNull(res.get(0).getLastName());
    Assert.assertNotNull(res.get(0).getPhoneNumber());
    Assert.assertNotNull(res.get(0).getCandidateDetail());
    Assert.assertFalse(new String(res.get(0).getCandidateDetail().getContent())
        .equals(new String(res.get(1).getCandidateDetail().getContent())));
    Assert.assertTrue(res.get(0).getLastName().equals("Prianto"));
    Assert.assertTrue(res.get(0).getLastName().equals(res.get(1).getLastName()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByLastNameNoResult() {
    List<Candidate> res = this.candidateDAO.findByLastName("asd");
    res.get(0);
  }

  @Test
  public void testFindByPhoneNumber() {
    List<Candidate> res = this.candidateDAO.findByPhoneNumber("1234567890");
    Assert.assertNotNull(res.get(0).getFirstName());
    Assert.assertNotNull(res.get(0).getEmailAddress());
    Assert.assertNotNull(res.get(0).getLastName());
    Assert.assertNotNull(res.get(0).getPhoneNumber());
    Assert.assertNotNull(res.get(0).getCandidateDetail());
    Assert.assertTrue(res.get(0).getPhoneNumber().equals("1234567890"));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByPhoneNumberSupposedOnlyTwoResult() {
    List<Candidate> res = this.candidateDAO.findByPhoneNumber("1234567890");
    res.get(2);
  }

  @Test
  public void testFindByStoreId() {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID + "haha");
    candidate.setMarkForDelete(false);
    candidate.setFirstName("testFindByIdAndStoreId Firstname");
    candidate.setLastName("testFindByIdAndStoreId Lastname");
    this.candidateDAO.save(candidate);
    Assert.assertTrue(this.candidateDAO.findByStoreId(STORE_ID + "haha").get(0).getFirstName()
        .equals("testFindByIdAndStoreId Firstname"));
  }

  @Test
  public void testFindByStoreIdAndMarkForDelete() {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID + "12");
    candidate.setMarkForDelete(false);
    candidate.setFirstName("testFindByIdAndStoreId Firstname");
    candidate.setLastName("testFindByIdAndStoreId Lastname");
    this.candidateDAO.save(candidate);
    Assert.assertTrue(this.candidateDAO.findByStoreIdAndMarkForDelete(STORE_ID + "12", false).get(0)
        .getFirstName().equals("testFindByIdAndStoreId Firstname"));
  }

  @Test
  public void testfindByStoreIdWithPageable() {
    // System.out.println(this.candidateDAO
    // .findByStoreId(STORE_ID, PageableHelper.generatePageable(0, 2000)).getContent().size());
    // System.out.println(this.candidateDAO
    // .findByStoreId(STORE_ID, PageableHelper.generatePageable(0, 4)).getContent().size());
    Assert.assertTrue(this.candidateDAO
        .findByStoreId(STORE_ID, PageableHelper.generatePageable(0, 4)).getContent().size() == 4);
    Assert.assertTrue(this.candidateDAO
        .findByStoreId(STORE_ID, PageableHelper.generatePageable(2, 4)).getContent().size() == 2);
  }

}
