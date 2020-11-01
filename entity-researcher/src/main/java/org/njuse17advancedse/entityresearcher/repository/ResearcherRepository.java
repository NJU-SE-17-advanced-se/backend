package org.njuse17advancedse.entityresearcher.repository;

import com.sun.istack.Nullable;
import java.util.List;
import org.njuse17advancedse.entityresearcher.entity.JpaResearcher;
import org.njuse17advancedse.entityresearcher.entity.ResearcherAffiliation;

public interface ResearcherRepository {
  JpaResearcher findResearcherById(String id);

  List<String> findById(
    String rid,
    @Nullable String start,
    @Nullable String end
  );
}
