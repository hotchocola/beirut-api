package com.gdn.x.beirut.solr.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

public interface CandidatePositionSolrRepository
    extends SolrCrudRepository<CandidatePositionSolr, String> {

  Page<CandidatePositionSolr> findByFirstNameContainingAndStoreId(String firstName, String storeId,
      Pageable pageable);

  Page<CandidatePositionSolr> findIdCandidateDistinctByLastNameContainingAndStoreId(String lastName,
      String storeId, Pageable pageable);

}
