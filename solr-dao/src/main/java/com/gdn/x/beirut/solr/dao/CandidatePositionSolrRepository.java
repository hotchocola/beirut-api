package com.gdn.x.beirut.solr.dao;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

public interface CandidatePositionSolrRepository
    extends SolrCrudRepository<CandidatePositionSolr, String> {
  // @Override
  // Page<CandidatePositionSolr> findAll(Pageable pageable);
}
