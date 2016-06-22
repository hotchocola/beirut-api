package com.gdn.x.beirut.solr.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

public interface CandidatePositionSolrRepository
    extends SolrCrudRepository<CandidatePositionSolr, String> {

  public Page<CandidatePositionSolr> findIdCandidateDistinctByFirstNameContainingAndStoreId(
      String firstName, String storeId, Pageable pageable);

}
