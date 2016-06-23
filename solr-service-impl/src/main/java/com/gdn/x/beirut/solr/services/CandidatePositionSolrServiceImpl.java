package com.gdn.x.beirut.solr.services;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SimpleStringCriteria;
import org.springframework.stereotype.Service;

import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

@Service(value = "candidatePositionSolrService")
public class CandidatePositionSolrServiceImpl implements CandidatePositionSolrService {

  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  @Override
  public Page<CandidatePositionSolr> executeSolrQuery(String query, String storeId,
      Pageable pageable) {
    String realQuery = query + " AND storeId:" + storeId;
    return candidatePositionTemplate.queryForPage(
        new SimpleQuery(new SimpleStringCriteria(realQuery), pageable),
        CandidatePositionSolr.class);
  }
}
