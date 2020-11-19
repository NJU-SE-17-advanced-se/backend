package org.njuse17advancedse.taskpartnershipanalysis.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;

@Repository
public class JpaResearcherRepository implements ResearcherRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<String> getPartnersByRid(String rid) {
    String sql;
    HashSet<String> hashSet = new HashSet<>();
    if (!containThisResearcher(rid)) {
      return Lists.newArrayList("no such researcher");
    }
    sql =
      "select pr.paper.id from paper_researcher pr where pr.researcher.id =:id ";
    List<String> papers = entityManager
      .createQuery(sql, String.class)
      .setParameter("id", rid)
      .getResultList();
    if (papers.size() > 0) {
      sql =
        "select pr.researcher.id from paper_researcher pr where pr.paper.id in :papers";
      hashSet.addAll(
        entityManager
          .createQuery(sql, String.class)
          .setParameter("papers", papers)
          .getResultList()
      );
    }
    hashSet.remove(rid);
    return new ArrayList<>(hashSet);
  }

  /**
   * 判断作者是否存在数据库中
   * @param rid 作者id
   * @return true or false
   */
  private boolean containThisResearcher(String rid) {
    String sql = "select count(r) from researcher r where r.id = :id";
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("id", rid)
        .getSingleResult()
        .toString()
    );
    return count != 0;
  }

  @Override
  public List<String> getPapersByRid(String rid, int start, int end) {
    String sql;
    if (containThisResearcher(rid)) {
      return Lists.newArrayList("no such researcher");
    }
    sql =
      "select pr.paper.id from paper_researcher pr where pr.researcher.id = :id and pr.paper.publicationDate between :start and :end";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("id", rid)
      .setParameter("start", start)
      .setParameter("end", end)
      .getResultList();
  }

  @Override
  public List<String> getReferencesByRid(String rid, int start, int end) {
    List<String> papers = getPapersByRid(rid, start, end);
    String sql =
      "select pr.reference.id from paper_reference pr where pr.paper.id in :papers";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("papers", papers)
      .getResultList();
  }

  @Override
  public int getCoAuthorNum(String rid, List<String> papers) {
    String sql =
      "select count(pr) from paper_researcher pr where pr.researcher.id=:id and pr.paper.id in :papers";
    return Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("id", rid)
        .setParameter("papers", papers)
        .getSingleResult()
        .toString()
    );
  }

  @Override
  public int getCitationNum(String rid, List<String> references) {
    String sql =
      "select count(pr) from paper_reference pr where pr.reference.id in :references and pr.paper.";
    return Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("id", rid)
        .setParameter("references", references)
        .getSingleResult()
        .toString()
    );
  }
}
