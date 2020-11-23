package org.njuse17advancedse.taskpartnershipanalysis.repository;

import java.util.HashMap;
import java.util.List;
import org.njuse17advancedse.taskpartnershipanalysis.entity.JpaPaper;

public interface ResearcherRepository {
  List<String> getPartnersByRid(String rid);

  List<String> getPapersByRid(String rid, int start, int end);

  List<String> getReferencesByPapers(List<String> papers);

  boolean notContainThisResearcher(String rid);

  HashMap<String, Integer> getCoAuthorMap(
    String researcherId,
    List<String> papers
  );

  HashMap<String, Integer> getCitationMap(
    String researcherId,
    List<String> references
  );

  List<String> getResearchersOfSimilarDomain(
    String rid,
    List<String> domains,
    List<String> partners
  );

  List<String> getDomainsByResearcherId(String rid);

  List<String> getNearPartnersByRid(String researchId);

  List<JpaPaper> getPaperDateById(String researchId);
}
