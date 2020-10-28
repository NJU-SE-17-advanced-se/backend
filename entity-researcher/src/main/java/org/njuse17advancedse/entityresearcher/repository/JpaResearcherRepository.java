package org.njuse17advancedse.entityresearcher.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.njuse17advancedse.entityresearcher.entity.JpaResearcher;
import org.springframework.stereotype.Repository;

@Repository
public class JpaResearcherRepository implements ResearcherRepository {
  @PersistenceContext //并不会真正注入EntityManager，因为它不是线程安全的
  private EntityManager entityManager;

  @Override
  public JpaResearcher findResearcherById(String id) {
    return entityManager.find(JpaResearcher.class, id);
  }
}
