package com.gdn.x.beirut.solr.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.gdn.x.beirut.solr.entity.CandidatePositionSolr;

public interface CandidatePositionSolrRepository
    extends SolrCrudRepository<CandidatePositionSolr, String> {

  public Page<CandidatePositionSolr> findByFirstNameContainingAndStoreId(String firstName,
      String storeId, Pageable pageable);

}
