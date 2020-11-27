package org.njuse17advancedse.taskimpactanalysis.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRepository implements AllRepository {
  @PersistenceContext //并不会真正注入EntityManager，因为它不是线程安全的
  private EntityManager entityManager;

  //某学者是否存在
  public boolean existsResearcherById(String id) {
    String sql = "select id from researcher where id=:id";
    return (
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", id)
        .getResultList()
        .size() !=
      0
    );
  }

  //某论文是否存在
  public boolean existsPaperById(String id) {
    String sql = "select id from paper where id=:id";
    return (
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", id)
        .getResultList()
        .size() !=
      0
    );
  }

  public List<Integer> getPaperQuotingTimes(String id) {
    String sql =
      "select paper.citation from paper join paper_researcher on paper.id= paper_researcher.pid where paper_researcher.rid=:researcherId";
    return entityManager
      .createQuery(sql, Integer.class)
      .setParameter("researcherId", id)
      .getResultList();
  }

  public Integer getSinglePaperQuotingTimes(String id) {
    String sql = "select citation from paper where id=:id";
    return entityManager
      .createQuery(sql, Integer.class)
      .setParameter("id", id)
      .getSingleResult();
  }
}
