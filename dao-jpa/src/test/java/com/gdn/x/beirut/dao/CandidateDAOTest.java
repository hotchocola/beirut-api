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

import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@TestExecutionListeners(listeners = {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class})
@Transactional(readOnly = false)
public class CandidateDAOTest {
  @Autowired
  private CandidateDAO candidateDAO;

  @Before
  public void initialize() {

    for (int i = 0; i < 10; i++) {
      Candidate newCandidate = new Candidate("1");
      newCandidate.setEmailaddress("egaprianto" + i + "@asd.com");
      newCandidate.setFirstname("Ega");
      newCandidate.setLastname("Prianto");
      newCandidate.setPhonenumber("123456789" + i);
      CandidateDetail candDetail = new CandidateDetail("1");
      candDetail.setContent(("ini PDF" + i).getBytes());
      newCandidate.setCandidatedetail(candDetail);
      this.candidateDAO.save(newCandidate);
    }

    Candidate newCandidate = new Candidate("1");
    newCandidate.setEmailaddress("egaprianto@asd.com");
    newCandidate.setFirstname("Ega");
    newCandidate.setLastname("Prianto");
    newCandidate.setPhonenumber("1234567890");
    CandidateDetail candDetail = new CandidateDetail("1");
    candDetail.setContent(("ini PDF").getBytes());
    newCandidate.setCandidatedetail(candDetail);
    this.candidateDAO.save(newCandidate);
  }

  @Test
  public void testFindByCreatedDateBetween() {
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);
    List<Candidate> res =
        this.candidateDAO.findByCreatedDateBetween(start.getTime(), end.getTime());
    for (Candidate candidate : res) {
      Assert.assertTrue(start.getTime().getTime() <= candidate.getCreatedDate().getTime()
          && end.getTime().getTime() >= candidate.getCreatedDate().getTime());
    }
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByCreatedDateBetweenNoResult() {
    GregorianCalendar start = new GregorianCalendar(1900, 1, 1);
    GregorianCalendar end = new GregorianCalendar(1900, 6, 1);
    List<Candidate> res =
        this.candidateDAO.findByCreatedDateBetween(start.getTime(), end.getTime());
    res.get(0);
  }

  @Test
  public void testFindByEmailaddress() {
    List<Candidate> res = this.candidateDAO.findByEmailaddress("egaprianto1@asd.com");
    Assert.assertNotNull(res.get(0).getFirstname());
    Assert.assertNotNull(res.get(0).getEmailaddress());
    Assert.assertNotNull(res.get(0).getLastname());
    Assert.assertNotNull(res.get(0).getPhonenumber());
    Assert.assertNotNull(res.get(0).getCandidatedetail());
    Assert.assertTrue(new String(res.get(0).getCandidatedetail().getContent()).equals("ini PDF1"));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByEmailAddressOnlyOneResult() {
    List<Candidate> res = this.candidateDAO.findByEmailaddress("egaprianto1@asd.com");
    res.get(1);
  }

  @Test
  public void testFindByFirstname() {
    List<Candidate> res = this.candidateDAO.findByFirstname("Ega");
    Assert.assertNotNull(res.get(0).getFirstname());
    Assert.assertNotNull(res.get(0).getEmailaddress());
    Assert.assertNotNull(res.get(0).getLastname());
    Assert.assertNotNull(res.get(0).getPhonenumber());
    Assert.assertNotNull(res.get(0).getCandidatedetail());
    Assert.assertFalse(new String(res.get(0).getCandidatedetail().getContent())
        .equals(new String(res.get(1).getCandidatedetail().getContent())));
    Assert.assertTrue(res.get(0).getFirstname().equals("Ega"));
    Assert.assertTrue(res.get(0).getFirstname().equals(res.get(1).getFirstname()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByFirstnameNoResult() {
    List<Candidate> res = this.candidateDAO.findByFirstname("asd");
    res.get(0);
  }

  @Test
  public void testFindByLastname() {
    List<Candidate> res = this.candidateDAO.findByLastname("Prianto");
    Assert.assertNotNull(res.get(0).getFirstname());
    Assert.assertNotNull(res.get(0).getEmailaddress());
    Assert.assertNotNull(res.get(0).getLastname());
    Assert.assertNotNull(res.get(0).getPhonenumber());
    Assert.assertNotNull(res.get(0).getCandidatedetail());
    Assert.assertFalse(new String(res.get(0).getCandidatedetail().getContent())
        .equals(new String(res.get(1).getCandidatedetail().getContent())));
    Assert.assertTrue(res.get(0).getLastname().equals("Prianto"));
    Assert.assertTrue(res.get(0).getLastname().equals(res.get(1).getLastname()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByLastnameNoResult() {
    List<Candidate> res = this.candidateDAO.findByLastname("asd");
    res.get(0);
  }

  @Test
  public void testFindByPhonenumber() {
    List<Candidate> res = this.candidateDAO.findByPhonenumber("1234567890");
    Assert.assertNotNull(res.get(0).getFirstname());
    Assert.assertNotNull(res.get(0).getEmailaddress());
    Assert.assertNotNull(res.get(0).getLastname());
    Assert.assertNotNull(res.get(0).getPhonenumber());
    Assert.assertNotNull(res.get(0).getCandidatedetail());
    Assert.assertNotNull(res.get(1).getFirstname());
    Assert.assertNotNull(res.get(1).getEmailaddress());
    Assert.assertNotNull(res.get(1).getLastname());
    Assert.assertNotNull(res.get(1).getPhonenumber());
    Assert.assertNotNull(res.get(1).getCandidatedetail());
    Assert.assertTrue(res.get(0).getPhonenumber().equals(res.get(1).getPhonenumber()));
    Assert.assertFalse(res.get(0).getEmailaddress().equals(res.get(1).getEmailaddress()));
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindByPhonenumberSupposedOnlyTwoResult() {
    List<Candidate> res = this.candidateDAO.findByPhonenumber("1234567890");
    res.get(2);
  }

}
