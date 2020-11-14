package org.njuse17advancedse.entityresearcher.repository;

import com.sun.istack.Nullable;
import java.util.List;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.dto.ISearchResult;

public interface ResearcherRepository {
  IResearcher getResearcherById(String id);

  IResearcherBasic getResearcherBasic(String id);

  List<String> findPapers(
    String id,
    @Nullable String start,
    @Nullable String end
  );

  List<String> findDomains(
    String id,
    @Nullable String start,
    @Nullable String end
  );

  List<String> findAffiliations(
    String rid,
    @Nullable String start,
    @Nullable String end
  );

  ISearchResult searchByCond(String keyword, int page);
}
