package org.njuse17advancedse.taskimpactanalysis.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.njuse17advancedse.taskimpactanalysis.entity.JpaPaper;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRepository implements AllRepository {
  @PersistenceContext //并不会真正注入EntityManager，因为它不是线程安全的
  private EntityManager entityManager;

  //某学者是否存在
  public boolean existsResearcherById(String id) {
    String sql = "select id from researcher where id=:id";
    return (
      !entityManager
        .createQuery(sql, String.class)
        .setParameter("id", id)
        .getResultList()
        .isEmpty()
    );
  }

  //某论文是否存在
  public boolean existsPaperById(String id) {
    String sql = "select id from paper where id=:id";
    return (
      !entityManager
        .createQuery(sql, String.class)
        .setParameter("id", id)
        .getResultList()
        .isEmpty()
    );
  }

  public List<Integer> getPaperQuotingTimes(String id) {
    String sql =
      "select p.citation from paper p join paper_researcher pr on p.id= pr.pid where pr.rid=:researcherId";
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

  public double getImpactFactor(String paperId) {
    JpaPaper p = entityManager.find(JpaPaper.class, paperId);
    return p.getPublication().getImpact();
  }
}
