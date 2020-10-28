package org.njuse17advancedse.entityresearcher.repository;

import org.njuse17advancedse.entityresearcher.entity.JpaResearcher;

public interface ResearcherRepository {
  JpaResearcher findResearcherById(String id);
}
