package com.gdn.x.beirut.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.gdn.common.web.param.PageableHelper;
import com.gdn.x.beirut.dao.CandidateDAO;
import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;

public class CandidateServiceTest {

  private static final String ID = "ID";

  private static final String STORE_ID = "MYID";

  private static final String FIRST_NAME = "RIZAL";

  private static final String LIKE_FIRST_NAME = "IZA";

  private static final String LIKE_LAST_NAME = "AHRI";

  private static final String LAST_NAME = "FAHRI";

  private static final Status STATUS = Status.APPLY;

  private static final Pageable DEFAULT_PAGEABLE = PageableHelper.generatePageable(0, 10);

  private static final String POSITION_ID = "POSITION_ID";

  @Mock
  private CandidateDAO candidateDao;

  @Mock
  private PositionDAO positionDao;

  @Mock
  private EventService eventService;

  @InjectMocks
  private CandidateServiceImpl candidateService;

  private Candidate candidate;

  private Candidate markForDeleteCandidate;

  private CandidateDetail candidateDetail;

  private Candidate candidateWithDetail;

  private List<Candidate> candidates;

  private Page<Candidate> candidatePage;

  private List<Candidate> candidateRanges;

  private Page<Candidate> candidateRangePage;

  private Position position;

  @Before
  public void initialize() {
    initMocks(this);
    this.position = new Position();
    this.position.setId(ID);
    this.position.setStoreId(STORE_ID);
    this.position.setTitle("Position Title");

    this.candidate = new Candidate();
    this.candidate.setId(ID);
    this.candidate.setStoreId(STORE_ID);
    this.candidate.setFirstName(FIRST_NAME);
    this.candidate.setLastName(LAST_NAME);

    this.markForDeleteCandidate = new Candidate();
    this.markForDeleteCandidate.setId(ID);
    this.markForDeleteCandidate.setStoreId(STORE_ID);
    this.markForDeleteCandidate.setFirstName(FIRST_NAME);
    this.markForDeleteCandidate.setLastName(LAST_NAME);
    this.markForDeleteCandidate.setMarkForDelete(true);

    this.candidateDetail = new CandidateDetail();
    this.candidateDetail.setId(ID);


    this.candidateWithDetail = new Candidate();
    this.candidateWithDetail.setId(ID);
    this.candidateWithDetail.setStoreId(STORE_ID);
    this.candidateWithDetail.setFirstName(FIRST_NAME);
    this.candidateWithDetail.setLastName(LAST_NAME);
    this.candidateWithDetail.setCandidateDetail(this.candidateDetail);

    this.candidates = new ArrayList<Candidate>();
    this.candidates.add(this.candidate);

    candidatePage = new PageImpl<Candidate>(candidates);

    when(this.candidateDao.findByFirstNameContainingAndStoreId(LIKE_FIRST_NAME, STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(this.candidatePage);
    when(this.candidateDao.findByLastNameContainingAndStoreId(LIKE_LAST_NAME, STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(this.candidatePage);
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    when(this.candidateDao.save(this.candidate)).thenReturn(this.candidate);
    candidateRanges = new ArrayList<>();
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar create = new GregorianCalendar(2016, 2, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);
    for (int i = 0; i < 10; i++) {
      Candidate newCandidate = new Candidate();
      newCandidate.setId(ID + " " + i);
      newCandidate.setStoreId(STORE_ID);
      newCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      newCandidate.setFirstName("Ega");
      newCandidate.setLastName("Prianto");
      newCandidate.setPhoneNumber("123456789" + i);
      newCandidate.setCreatedDate(create.getTime());
      CandidateDetail candDetail = new CandidateDetail();
      candDetail.setContent(("ini PDF" + i).getBytes());
      newCandidate.setCandidateDetail(candDetail);
      candidates.add(newCandidate);
    }

    Candidate newCandidate = new Candidate();
    newCandidate.setId(ID);
    newCandidate.setStoreId(STORE_ID);
    newCandidate.setEmailAddress("egaprianto@asd.com");
    newCandidate.setFirstName("Ega");
    newCandidate.setLastName("Prianto");
    newCandidate.setPhoneNumber("1234567890");
    newCandidate.setCreatedDate(create.getTime());
    CandidateDetail candDetail = new CandidateDetail();
    candDetail.setContent(("ini PDF").getBytes());
    newCandidate.setCandidateDetail(candDetail);
    candidateRanges.add(newCandidate);

    candidateRangePage = new PageImpl<Candidate>(candidateRanges);

    for (int i = 0; i < 10; i++) {
      Candidate toBeDeletedCandidate = new Candidate();
      toBeDeletedCandidate.setId(ID + " " + i);
      toBeDeletedCandidate.setStoreId(STORE_ID);
      toBeDeletedCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      toBeDeletedCandidate.setFirstName("Ega");
      toBeDeletedCandidate.setLastName("Prianto");
      toBeDeletedCandidate.setPhoneNumber("123456789" + i);
      toBeDeletedCandidate.setCreatedDate(create.getTime());
      toBeDeletedCandidate.setMarkForDelete(false);
      CandidateDetail candidateDetail = new CandidateDetail();
      candidateDetail.setContent(("ini PDF" + i).getBytes());
      toBeDeletedCandidate.setCandidateDetail(candidateDetail);
      candidates.add(toBeDeletedCandidate);
      Mockito.when(this.candidateDao.findByIdAndMarkForDelete(ID + " " + i, false))
          .thenReturn(toBeDeletedCandidate);
    }

    when(this.candidateDao.findByCreatedDateBetweenAndStoreId(start.getTime(), end.getTime(),
        STORE_ID, DEFAULT_PAGEABLE)).thenReturn(candidateRangePage);
  }

  @After
  public void noMoreTransaction() {
    Mockito.verifyNoMoreInteractions(this.candidateDao);
    Mockito.verifyNoMoreInteractions(this.positionDao);
  }


  @Test
  public void testApplyNewPosition() throws Exception {
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    List<Position> positions = new ArrayList<>();
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Position position = new Position();
      position.setTitle("Title : " + i);
      position.setId(POSITION_ID + i);
      position.setStoreId(STORE_ID);
      positions.add(position);
      positionIds.add(POSITION_ID + i);
    }
    this.candidateService.applyNewPosition(ID, positionIds);
    Mockito.when(this.positionDao.findAll(positionIds)).thenReturn(positions);

  }

  @Test
  public void testGetAllCandidates() {
    Candidate cand1 = new Candidate();
    cand1.setId(ID);
    cand1.setStoreId(STORE_ID);
    Candidate cand2 = new Candidate();
    cand2.setId(ID);
    cand2.setStoreId(STORE_ID);
    List<Candidate> candidates = new ArrayList<Candidate>();
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
  public void testGetAllCandidatesByStoreId() {
    List<Candidate> candidates = new ArrayList<Candidate>();
    candidates.add(this.candidate);
    Page<Candidate> pageCandidates =
        new PageImpl<>(candidates, DEFAULT_PAGEABLE, candidates.size());
    Mockito.when(this.candidateDao.findByStoreId(STORE_ID, DEFAULT_PAGEABLE))
        .thenReturn(pageCandidates);
  }

  @Test
  public void testGetCandidate() throws Exception {
    this.candidate.setEmailAddress("email address");
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    Candidate result = this.candidateService.getCandidate(ID);
    result.setEmailAddress("email address");
    // Black Box Test
    assertTrue(result.getId().equals(this.candidate.getId()));
    assertTrue(result.getStoreId().equals(this.candidate.getStoreId()));
    assertTrue(result.getFirstName().equals(this.candidate.getFirstName()));
    assertTrue(result.getLastName().equals(this.candidate.getLastName()));
    assertTrue(result.getEmailAddress().equals(this.candidate.getEmailAddress()));
    // White Box Test
    Mockito.verify(this.candidateDao, Mockito.times(1)).findOne(ID);
  }

  @Test(expected = Exception.class)
  public void testGetCandidateAndReturnException() throws Exception {
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(null);
    try {
      this.candidateService.getCandidate(ID);
    } catch (Exception e) {
      Mockito.verify(this.candidateDao, times(1)).findOne(ID);
      throw e;
    }
  }

  @Test
  public void testGetCandidateByIdAndStoreIdEager() throws Exception {
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    assertTrue(this.candidateDao.findOne(ID).equals(this.candidate));
    this.candidateService.getCandidateByIdAndStoreIdEager(ID, STORE_ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test
  public void testGetCandidateByIdAndStoreIdLazy() throws Exception {
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    assertTrue(this.candidateDao.findOne(ID).equals(this.candidate));
    this.candidateService.getCandidateByIdAndStoreIdLazy(ID, STORE_ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test
  public void testGetCandidateDetail() throws Exception {
    // Black Box Test
    assertTrue(this.candidateDao.findOne(ID).equals(this.candidate));
    // White Box Test
    this.candidateService.getCandidateDetailAndStoreId(ID, STORE_ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test
  public void testGetCandidatePositionWithLogs() throws Exception {
    Candidate candidate = new Candidate();
    candidate.setId(ID);
    candidate.setStoreId(STORE_ID);
    candidate.setFirstName("ZAL");
    Position position = new Position();
    position.setId(ID);
    position.setStoreId(STORE_ID);
    position.setTitle("ZALTITLE");
    CandidatePosition candPost = new CandidatePosition();
    candPost.setCandidate(candidate);
    candPost.setPosition(position);
    Set<CandidatePosition> sets = new HashSet<CandidatePosition>();
    sets.add(candPost);
    candidate.setCandidatePositions(sets);
    position.setCandidatePositions(sets);
    when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    when(this.positionDao.findOne(ID)).thenReturn(position);
    this.candidateService.getCandidatePositionWithLogs(ID, ID);
    verify(this.positionDao, times(1)).findOne(ID);
    verify(this.candidateDao, times(1)).findOne(ID);
  }

  @Test
  public void testMarkForDelete() throws Exception {
    Mockito.when(this.candidateDao.findByIdAndMarkForDelete(ID, false)).thenReturn(candidate);
    this.candidateService.markForDelete(ID);
    verify(this.candidateDao, times(1)).findByIdAndMarkForDelete(Mockito.anyString(),
        Mockito.eq(false));
    verify(this.candidateDao, times(1)).save(Mockito.any(Candidate.class));
  }

  @Test
  public void testMarkForDeleteBulk() throws Exception {
    List<String> ids = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      String id = ID + " " + i;
      ids.add(id);
    }
    this.candidateService.markForDelete(ids);
    // Black Box Test
    // White Box Test
    verify(this.candidateDao, times(10)).findByIdAndMarkForDelete(Mockito.anyString(),
        Mockito.eq(false));
    final Candidate candidate = this.candidate;
    candidate.setMarkForDelete(true);
    verify(this.candidateDao, times(10)).save(Mockito.any(Candidate.class));
  }

  @Test
  public void testSave() throws Exception {
    Mockito.when(this.candidateDao.findOne(ID)).thenReturn(candidate);
    List<Position> positions = new ArrayList<>();
    List<String> positionIds = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Position position = new Position();
      position.setTitle("Title : " + i);
      position.setId(POSITION_ID + i);
      position.setStoreId(STORE_ID);
      positions.add(position);
      positionIds.add(POSITION_ID + i);
    }
    // Black Box Test
    // White Box Test
    this.candidateService.createNew(this.candidate, positionIds);
    verify(this.candidateDao, times(1)).save(this.candidate);
    verify(this.positionDao, times(1)).findAll(positionIds);
  }

  @Test
  public void testSearchByCreatedDateBetween() {
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);

    List<Candidate> result =
        this.candidateService.searchByCreatedDateBetweenAndStoreId(start.getTime(), end.getTime(),
            STORE_ID, DEFAULT_PAGEABLE).getContent();
    Page<Candidate> pageRes = new PageImpl<>(result, DEFAULT_PAGEABLE, result.size());
    Mockito.when(this.candidateDao.findByCreatedDateBetweenAndStoreId(start.getTime(),
        end.getTime(), STORE_ID, DEFAULT_PAGEABLE)).thenReturn(pageRes);

    // Black Box Test
    Assert.assertTrue(result.size() == 1);
    Assert.assertTrue(result.get(0).getStoreId().equals(this.candidate.getStoreId()));
    // White Box Test
    verify(this.candidateDao, times(1)).findByCreatedDateBetweenAndStoreId(start.getTime(),
        end.getTime(), STORE_ID, DEFAULT_PAGEABLE);
  }

  @Test
  public void testSearchByFirstName() throws Exception {
    // White Box Test
    this.candidateService.searchByFirstNameContainAndStoreId(LIKE_FIRST_NAME, STORE_ID,
        DEFAULT_PAGEABLE);
    verify(this.candidateDao, times(1)).findByFirstNameContainingAndStoreId(LIKE_FIRST_NAME,
        STORE_ID, DEFAULT_PAGEABLE);
  }

  @Test
  public void testSearchByLastName() {
    Page<Candidate> searchByLastNameContainAndStoreId = this.candidateService
        .searchByLastNameContainAndStoreId(LIKE_LAST_NAME, STORE_ID, DEFAULT_PAGEABLE);
    // White Box Test (CEK PEMANGGILAN)
    verify(this.candidateDao, times(1)).findByLastNameContainingAndStoreId(LIKE_LAST_NAME, STORE_ID,
        DEFAULT_PAGEABLE);
    // Black Box Test
    Assert.assertTrue(searchByLastNameContainAndStoreId.getContent().size() == 1);
  }

  @Test
  public void testSearchCandidateByEmailAddress() {
    // List<Candidate> res = new ArrayList<>();
    // for (Candidate candidate : candidateRanges) {
    // if (candidate.getEmailAddress().equals("egaprianto@asd.com")) {
    // res.add(candidate);
    // }
    // }
    candidate.setEmailAddress("egaprianto@asd.com");
    when(this.candidateDao.findByEmailAddressAndStoreId("egaprianto@asd.com", STORE_ID))
        .thenReturn(candidate);
    Candidate result = this.candidateService
        .searchCandidateByEmailAddressAndStoreId("egaprianto@asd.com", STORE_ID);
    // Black Box Test
    Assert.assertTrue(result.equals(candidate));
    // White Box Test
    verify(this.candidateDao, times(1)).findByEmailAddressAndStoreId("egaprianto@asd.com",
        STORE_ID);
  }

  @Test
  public void testSearchCandidateByPhoneNumber() {
    List<Candidate> res = new ArrayList<>();
    for (Candidate candidate : candidates) {
      if (candidate.getPhoneNumber() != null && candidate.getPhoneNumber().equals("1234567890")) {
        res.add(candidate);
      }
    }
    Page<Candidate> pageRes = new PageImpl<>(res, DEFAULT_PAGEABLE, res.size());
    when(this.candidateDao.findByPhoneNumberContainingAndStoreId("1234567890", STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(pageRes);
    Page<Candidate> result = this.candidateService
        .searchCandidateByPhoneNumberContainAndStoreId("1234567890", STORE_ID, DEFAULT_PAGEABLE);
    // Black Box Test
    Assert.assertTrue(result.getContent().equals(res));
    // White Box Test
    verify(this.candidateDao, times(1)).findByPhoneNumberContainingAndStoreId("1234567890",
        STORE_ID, DEFAULT_PAGEABLE);
  }

  @Test
  public void testSearchCandidateByPhoneNumberLike() {
    List<Candidate> res = new ArrayList<>();
    for (Candidate candidate : candidates) {
      if (candidate.getPhoneNumber() != null && candidate.getPhoneNumber().contains("123456789")) {
        res.add(candidate);
      }
    }
    when(this.candidateDao.findByPhoneNumberContainingAndStoreId("123456789", STORE_ID,
        DEFAULT_PAGEABLE)).thenReturn(candidatePage);
    Page<Candidate> result = this.candidateService
        .searchCandidateByPhoneNumberContainAndStoreId("123456789", STORE_ID, DEFAULT_PAGEABLE);
    // Black Box Test
    Assert.assertTrue(result.getContent().size() == 1);
    // White Box Test
    verify(this.candidateDao, times(1)).findByPhoneNumberContainingAndStoreId("123456789", STORE_ID,
        DEFAULT_PAGEABLE);
  }

  // getCandidatePositionWithLogs(String idCandidate, String idPosition)
  @Test
  public void testUpdateCandidateDetail() throws Exception {
    String detilTest = "contoh konten!!!";
    String firstTest = "Eve";
    String lastTest = "Adam";
    String phoneTest = "29382932";
    String emailTest = "email";
    candidateWithDetail.setPhoneNumber(phoneTest);
    candidateWithDetail.setEmailAddress(emailTest);
    candidateWithDetail.setFirstName(firstTest);
    candidateWithDetail.setLastName(lastTest);
    candidateWithDetail.getCandidateDetail().setContent((detilTest).getBytes());
    when(this.candidateDao.findOne(ID)).thenReturn(candidateWithDetail);
    Candidate testCandidate = candidateWithDetail;

    this.candidateService.updateCandidateDetail(STORE_ID, testCandidate);
    testCandidate.setPhoneNumber(phoneTest);
    testCandidate.setEmailAddress(emailTest);
    testCandidate.setFirstName(firstTest);
    testCandidate.setLastName(lastTest);
    testCandidate.getCandidateDetail().setContent((detilTest).getBytes());

    assertTrue(testCandidate.getFirstName().equals(candidateWithDetail.getFirstName()));
    assertTrue(testCandidate.getLastName().equals(candidateWithDetail.getLastName()));
    assertTrue(testCandidate.getEmailAddress().equals(candidateWithDetail.getEmailAddress()));
    assertTrue(testCandidate.getPhoneNumber().equals(candidateWithDetail.getPhoneNumber()));
    assertEquals(testCandidate.getCandidateDetail().getContent(),
        candidateWithDetail.getCandidateDetail().getContent());

    verify(this.candidateDao, times(1)).findOne(ID);
    verify(this.candidateDao, times(1)).save(Mockito.any(Candidate.class));
  }


  @Test
  public void testUpdateCandidateStatus() throws Exception {
    when(this.positionDao.findOne(ID)).thenReturn(this.position);
    Candidate testCandidate = candidate;
    this.candidateService.updateCandidateStatus(STORE_ID, testCandidate.getId(), ID, STATUS);
    verify(this.candidateDao, times(1)).findOne(ID);
    verify(this.positionDao, times(1)).findOne(ID);
    /*
     * Hibernate.initialize(testCandidate.getCandidatePositions());
     * testCandidate.getCandidatePositions().stream() .filter(candidatePosition ->
     * candidatePosition.getPosition().equals(position)) .forEach(candidatePosition -> {
     * candidatePosition.getStatusLogs().add(new StatusLog(candidatePosition, STATUS));
     * candidatePosition.setStatus(STATUS); // add missing setter zal });
     */
    verify(this.candidateDao, times(1)).save(testCandidate);
  }
}
