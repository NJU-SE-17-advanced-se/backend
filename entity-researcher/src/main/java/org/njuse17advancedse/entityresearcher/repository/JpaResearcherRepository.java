package org.njuse17advancedse.entityresearcher.repository;

import com.sun.istack.Nullable;
import java.util.List;
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

  @Override
  public List<String> findById(
    String rid,
    @Nullable String start,
    @Nullable String end
  ) {
    int startDate = 0;
    int endDate = 9999;
    String sql;
    List<String> affiliations;
    if (start != null) {
      startDate = Integer.parseInt(start);
    }
    if (end != null) {
      endDate = Integer.parseInt(end);
    }
    sql =
      "select ra.affiliation.id from researcher_affiliation ra where ra.researcher.id = :rid and ra.year between :a and :b";
    affiliations =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("rid", rid)
        .setParameter("a", startDate)
        .setParameter("b", endDate)
        .getResultList();
    return affiliations;
  }
}
