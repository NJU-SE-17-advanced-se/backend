package org.njuse17advancedse.taskimpactanalysis.data;

import io.swagger.models.auth.In;
import java.util.ArrayList;
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
      "select p.citation from paper p join paper_researcher pr on p.id= pr.pid where pr.rid=:researcherId";
    //    List<String> res= entityManager
    //      .createQuery(sql, String.class)
    //      .setParameter("researcherId", id)
    //      .getResultList();
    //    List<Integer> i=new ArrayList<>();
    //    for(String r:res)
    //      i.add(Integer.valueOf(r));
    //    return i;
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
