package org.njuse17advancedse.taskimpactanalysis.data;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface AllRepository {
  @Cacheable("getPaperQuotingTimes")
  List<Integer> getPaperQuotingTimes(String researcherId);

  @Cacheable("existsPaperById")
  boolean existsPaperById(String paperId);

  @Cacheable("existsResearcherById")
  boolean existsResearcherById(String researcherId);

  @Cacheable("getSinglePaperQuotingTimes")
  Integer getSinglePaperQuotingTimes(String id);

  @Cacheable("getImpactFactor")
  double getImpactFactor(String paperId);
}
