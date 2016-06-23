package com.gdn.x.beirut.solr.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.data.solr.core.query.result.SolrResultPage;

import com.gdn.common.web.param.PageableHelper;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

public class CandidatePositionSolrTest {

  private static final Pageable DEFAULT_PAGEABLE = PageableHelper.generatePageable(0, 10);

  public String ID_CANDIDATE = "idC";

  public String ID_POSITION = "idP";

  public String EMAIL_ADDRESS = "em@em.em";

  public String STORE_ID = "storeId";

  public String FIRST_NAME = "fahri";

  public String LAST_NAME = "zal";

  public String PHONE_NUMBER = "666";

  public Date CREATED_DATE = new Date(System.currentTimeMillis());

  public String TITLE = "myTitle";

  public String STATUS = "CEO";

  public String PLAIN_QUERY = "name:izal OR price:9";

  public String STORE_ID_QUERY = "storeId:" + STORE_ID;

  public String REAL_QUERY = STORE_ID_QUERY + " AND " + PLAIN_QUERY;


  public CandidatePositionSolr candidatePositionSolr;

  @Mock
  private SolrTemplate candidatePositionTemplate;

  @InjectMocks
  private CandidatePositionSolrServiceImpl candidatePositionSolrService;

  @Before
  public void initialize() {
    initMocks(this);
  }

  @After
  public void noMoreTransaction() {
    Mockito.verifyNoMoreInteractions(candidatePositionTemplate);
  }

  // @Test
  public void testExecuteSolrQueryFindAll() {
    List<CandidatePositionSolr> candidatePositionSolrList = new ArrayList<CandidatePositionSolr>();
    candidatePositionSolrList.add(this.candidatePositionSolr);

    SolrResultPage<CandidatePositionSolr> candidatePositionSolrScoredPage =
        new SolrResultPage<CandidatePositionSolr>(candidatePositionSolrList);

    // when(candidatePositionTemplate.queryForPage(
    // new SimpleQuery(new SimpleStringCriteria(REAL_QUERY), DEFAULT_PAGEABLE),
    // CandidatePositionSolr.class)).thenReturn(candidatePositionSolrScoredPage);

    Mockito.when(candidatePositionTemplate.queryForPage(
        new SimpleQuery(new SimpleStringCriteria(REAL_QUERY), DEFAULT_PAGEABLE),
        CandidatePositionSolr.class)).thenReturn(candidatePositionSolrScoredPage);

    this.candidatePositionSolrService.executeSolrQuery(PLAIN_QUERY, STORE_ID, DEFAULT_PAGEABLE);

    verify(this.candidatePositionTemplate, times(1)).queryForPage(
        new SimpleQuery(new SimpleStringCriteria(REAL_QUERY), DEFAULT_PAGEABLE),
        CandidatePositionSolr.class);

  }
}
