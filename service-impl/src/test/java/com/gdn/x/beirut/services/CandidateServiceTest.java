package com.gdn.x.beirut.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.gdn.x.beirut.dao.CandidateDAO;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;

public class CandidateServiceTest {

  private static final String ID = "ID";

  private static final String STORE_ID = "MYID";

  private static final String FIRST_NAME = "RIZAL";

  private static final String LIKE_FIRST_NAME = "IZA";

  private static final String LIKE_LAST_NAME = "AHRI";

  private static final String LAST_NAME = "FAHRI";

  @Mock
  private CandidateDAO candidateDao;

  @InjectMocks
  private CandidateServiceImpl candidateService;

  private Candidate candidate;

  private Candidate markForDeleteCandidate;

  private CandidateDetail candidateDetail;

  private Candidate candidateWithDetail;

  private List<Candidate> candidates;

  @Before
  public void initialize() {
    initMocks(this);
    this.candidate = new Candidate(STORE_ID);
    this.candidate.setFirstname(FIRST_NAME);
    this.candidate.setLastname(LAST_NAME);

    this.markForDeleteCandidate = new Candidate(STORE_ID);
    this.markForDeleteCandidate.setFirstname(FIRST_NAME);
    this.markForDeleteCandidate.setLastname(LAST_NAME);
    this.markForDeleteCandidate.setMarkForDelete(true);

    this.candidateDetail = new CandidateDetail(STORE_ID);

    this.candidateWithDetail = new Candidate(STORE_ID);
    this.candidateWithDetail.setFirstname(FIRST_NAME);
    this.candidateWithDetail.setLastname(LAST_NAME);
    this.candidateWithDetail.setCandidatedetail(this.candidateDetail);

    this.candidates = new ArrayList<Candidate>();
    this.candidates.add(this.candidate);

    when(this.candidateDao.findByFirstnameLike(LIKE_FIRST_NAME)).thenReturn(this.candidates);
    when(this.candidateDao.findByLastnameLike(LIKE_LAST_NAME)).thenReturn(this.candidates);
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    when(this.candidateDao.save(this.candidate)).thenReturn(this.candidate);
  }

  @After
  public void noMoreTransaction() {
    verifyNoMoreInteractions(this.candidateDao);
  }

  @Test
  public void testFindByCreatedDateBetween() {
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar create = new GregorianCalendar(2016, 2, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);
    List<Candidate> candidates = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Candidate newCandidate = new Candidate("1");
      newCandidate.setEmailaddress("egaprianto" + i + "@asd.com");
      newCandidate.setFirstname("Ega");
      newCandidate.setLastname("Prianto");
      newCandidate.setPhonenumber("123456789" + i);
      newCandidate.setCreatedDate(new Date(System.currentTimeMillis()));
      CandidateDetail candDetail = new CandidateDetail("1");
      candDetail.setContent(("ini PDF" + i).getBytes());
      newCandidate.setCandidatedetail(candDetail);
      candidates.add(newCandidate);
    }

    Candidate newCandidate = new Candidate("1");
    newCandidate.setEmailaddress("egaprianto@asd.com");
    newCandidate.setFirstname("Ega");
    newCandidate.setLastname("Prianto");
    newCandidate.setPhonenumber("1234567890");
    newCandidate.setCreatedDate(new Date(System.currentTimeMillis()));
    CandidateDetail candDetail = new CandidateDetail("1");
    candDetail.setContent(("ini PDF").getBytes());
    newCandidate.setCandidatedetail(candDetail);
    candidates.add(newCandidate);

    when(this.candidateDao.findByCreatedDateBetween(start.getTime(), end.getTime()))
        .thenReturn(candidates);

    List<Candidate> result =
        this.candidateService.searchByCreatedDateBetween(start.getTime(), end.getTime());
    // Black Box Test
    Assert.assertTrue(result.equals(candidates));
    // White Box Test
    verify(this.candidateDao, times(1)).findByCreatedDateBetween(start.getTime(), end.getTime());
  }

  @Test
  public void testGetAllCandidates() {
    final Candidate cand1 = new Candidate(STORE_ID);
    final Candidate cand2 = new Candidate(STORE_ID);
    final List<Candidate> candidates = new ArrayList<Candidate>();
    candidates.add(cand1);
    candidates.add(cand2);
    when(this.candidateDao.findAll()).thenReturn(candidates);

    // Black Box Test
    Assert.assertTrue(this.candidateDao.findAll() == candidates);
    // White Box Test
    this.candidateService.getAllCandidates();
    verify(this.candidateDao, times(2)).findAll();
  }

  @Test
  public void testGetCandidate() throws Exception {
    // Black Box Test
    Assert.assertTrue(this.candidateDao.findOne(ID) == this.candidate);
    // White Box Test
    this.candidateService.getCandidate(ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test
  public void testGetCandidateDetail() throws Exception {
    // Black Box Test
    assertTrue(this.candidateDao.findOne(ID).equals(this.candidate));
    // White Box Test
    this.candidateService.getCandidateDetail(ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test
  public void testMarkForDelete() {
    // Black Box Test

    // White Box Test
    this.candidateService.markForDelete(ID);
    verify(this.candidateDao, times(1)).findOne(ID);
    final Candidate candidate = this.candidate;
    candidate.setMarkForDelete(true);
    verify(this.candidateDao, times(1)).save(this.markForDeleteCandidate);
  }

  @Test
  public void testSave() {
    // Black Box Test
    assertTrue(this.candidateDao.save(this.candidate).equals(this.candidate));
    // White Box Test
    this.candidateService.save(this.candidate);
    verify(this.candidateDao, times(2)).save(this.candidate);
  }

  @Test
  public void testSearchByFirstname() {
    // Black Box Test
    Assert
        .assertTrue(this.candidateDao.findByFirstnameLike(LIKE_FIRST_NAME).equals(this.candidates));
    // White Box Test
    this.candidateService.searchByFirstname(LIKE_FIRST_NAME);
    verify(this.candidateDao, times(2)).findByFirstnameLike(LIKE_FIRST_NAME);
  }

  @Test
  public void testSearchByLastname() {
    // Black Box Test
    Assert.assertTrue(this.candidateDao.findByLastnameLike(LIKE_LAST_NAME).equals(this.candidates));
    // White Box Test (CEK PEMANGGILAN)
    this.candidateService.searchByLastname(LIKE_LAST_NAME);
    verify(this.candidateDao, times(2)).findByLastnameLike(LIKE_LAST_NAME);
  }

  @Test
  public void testSetCandidateDetail() throws Exception {
    // Black Box Test

    // White Box Test
    this.candidateService.setCandidateDetail(ID, this.candidateDetail);
    verify(this.candidateDao, times(1)).findOne(ID);
    verify(this.candidateDao, times(1)).save(this.candidateWithDetail);
  }

}
