package com.gdn.x.beirut.solr.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.solr.core.SolrTemplate;

public class CandidatePositionSolrTest {
  @Mock
  private SolrTemplate candidatePositionTemplate;

  @InjectMocks
  private CandidatePositionSolrService candidatePositionSolrService;

  @Before
  public void initialize() {

  }

  @Test
  public void testExecuteSolrQuery() {

  }
}
