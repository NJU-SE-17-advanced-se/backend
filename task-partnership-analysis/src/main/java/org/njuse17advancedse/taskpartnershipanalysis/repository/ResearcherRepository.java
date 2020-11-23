package org.njuse17advancedse.taskpartnershipanalysis.repository;

import java.util.List;

public interface ResearcherRepository {
  List<String> getPartnersByRid(String rid);

  List<String> getPapersByRid(String rid, int start, int end);

  List<String> getReferencesByRid(String rid, int start, int end);

  int getCoAuthorNum(String rid, List<String> papers);

  int getCitationNum(String rid, List<String> references);
}
