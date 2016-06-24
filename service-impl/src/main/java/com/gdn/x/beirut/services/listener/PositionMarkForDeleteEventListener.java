package com.gdn.x.beirut.services.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.PartialUpdate;

import com.gdn.common.base.domainevent.subscriber.DomainEventListener;
import com.gdn.x.beirut.domain.event.model.PositionMarkForDelete;
import com.gdn.x.beirut.solr.entities.CandidatePositionSolr;

public class PositionMarkForDeleteEventListener
    implements DomainEventListener<PositionMarkForDelete> {
  private static final Logger LOG = LoggerFactory.getLogger(CandidateInsertNewEventListener.class);

  @Resource(name = "xcandidatePositionTemplate")
  private SolrTemplate candidatePositionTemplate;

  @Override
  public void onDomainEventConsumed(PositionMarkForDelete message) throws Exception {
    LOG.info("consuming message from kafka : {}", new Object[] {message});
    PartialUpdate update = new PartialUpdate("idPosition", message.getId());
    update.setValueOfField(CandidatePositionSolr.MARK_FOR_DELETE, true);
    candidatePositionTemplate.saveBean(update);
    candidatePositionTemplate.commit();
  }

}
