package org.njuse17advancedse.taskimpactanalysis.data;

import java.util.List;

public interface AllRepository {
  List<Integer> getPaperQuotingTimes(String paperId);

  boolean existsPaperById(String paperId);

  boolean existsResearcherById(String researcherId);

  Integer getSinglePaperQuotingTimes(String id);
}