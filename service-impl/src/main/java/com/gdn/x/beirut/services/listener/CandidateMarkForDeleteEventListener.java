package com.gdn.x.beirut.services.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.common.base.domainevent.subscriber.SubscribeDomainEvent;
import com.gdn.common.base.mapper.GdnMapper;
import com.gdn.x.beirut.domain.event.model.CandidateMarkForDelete;
import com.gdn.x.beirut.domain.event.model.DomainEventName;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

@Service
@SubscribeDomainEvent(DomainEventName.CANDIDATE_MARK_FOR_DELETE)
public class CandidateMarkForDeleteEventListener
    implements DomainEventListener<CandidateMarkForDelete> {
  private static final Logger LOG = LoggerFactory.getLogger(CandidateInsertNewEventListener.class);

  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  @Autowired
  private GdnMapper gdnMapper;

  @Override
  public void onDomainEventConsumed(CandidateMarkForDelete message) throws Exception {
    LOG.info("consuming message from kafka (Mark for delete) : {}", new Object[] {message});
    ScoredPage<CandidatePositionSolr> candidatePositionSolrPage =
        this.candidatePositionTemplate.queryForPage(
            new SimpleQuery("idCandidate:" + message.getId()), CandidatePositionSolr.class);
    LOG.info("Query dapet : " + candidatePositionSolrPage.getNumberOfElements());
    for (CandidatePositionSolr candidatePositionSolr : candidatePositionSolrPage) {
      candidatePositionSolr.setMarkForDelete(true);
    }
    LOG.info("DEBUG IZAL" + candidatePositionSolrPage.getContent().get(0).isMarkForDelete());
    this.candidatePositionTemplate.saveBeans(candidatePositionSolrPage.getContent());
    this.candidatePositionTemplate.commit();
  }
}
