package org.njuse17advancedse.taskreviewerrecommendation.repository;

import com.sun.istack.Nullable;
import java.util.List;

public interface PaperRepository {
  List<String> getResearcherIdsFromReferences(
    List<String> references,
    List<String> partners
  );

  List<String> getResearchersFromPublication(
    String paperJournal,
    int date,
    List<String> partners
  );

  List<String> getResearcherFromSimilarDomain(
    List<String> domains,
    int date,
    @Nullable List<String> partners
  );

  List<String> getPastPartners(List<String> researcherIds);

  List<String> getPartnersByAffiliation(List<String> researcherIds);
}
