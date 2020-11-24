package org.njuse17advancedse.taskpartnershipanalysis.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.njuse17advancedse.taskpartnershipanalysis.entity.JpaPaper;
import org.springframework.stereotype.Repository;

@Repository
public class JpaResearcherRepository implements ResearcherRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<String> getPartnersByRid(String rid) {
    String sql;
    sql =
      "select pr.paper.id from paper_researcher pr where pr.researcher.id =:id ";
    List<String> papers = entityManager
      .createQuery(sql, String.class)
      .setParameter("id", rid)
      .getResultList();
    if (papers.size() > 0) {
      sql =
        "select distinct pr.researcher.id from paper_researcher pr where pr.paper.id in :papers";
    }
    List<String> partners = entityManager
      .createQuery(sql, String.class)
      .setParameter("papers", papers)
      .getResultList();
    partners.remove(rid);
    return partners;
  }

  /**
   * 判断作者是否存在数据库中
   * @param rid 作者id
   * @return true or false
   */
  @Override
  public boolean notContainThisResearcher(String rid) {
    String sql = "select count(r) from researcher r where r.id = :id";
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("id", rid)
        .getSingleResult()
        .toString()
    );
    return count == 0;
  }

  @Override
  public List<String> getPapersByRid(String rid, int start, int end) {
    String sql;
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
  public List<String> getReferencesByPapers(List<String> papers) {
    String sql =
      "select pr.reference.id from paper_reference pr where pr.paper.id in :papers";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("papers", papers)
      .getResultList();
  }

  @Override
  public HashMap<String, Integer> getCoAuthorMap(
    String researcherId,
    List<String> papers
  ) {
    HashMap<String, Integer> hashMap = new HashMap<>();
    String sql =
      "select pr.researcher.id from paper_researcher pr where pr.paper.id in :papers";
    List<String> partners = entityManager
      .createQuery(sql, String.class)
      .setParameter("papers", papers)
      .getResultList();
    for (String partner : partners) {
      Integer count = hashMap.get(partner);
      hashMap.put(partner, (count == null) ? 1 : count + 1);
    }
    hashMap.remove(researcherId);
    return hashMap;
  }

  @Override
  public HashMap<String, Integer> getCitationMap(
    String researcherId,
    List<String> references
  ) {
    HashMap<String, Integer> hashMap = new HashMap<>();
    String sql =
      "select pra.researcher.id from paper_researcher pra,paper_reference pre where pra.paper.id = pre.paper.id and pre.reference.id in :references";
    List<String> partners = entityManager
      .createQuery(sql, String.class)
      .setParameter("references", references)
      .getResultList();
    for (String partner : partners) {
      Integer count = hashMap.get(partner);
      hashMap.put(partner, (count == null) ? 1 : count + 1);
    }
    hashMap.remove(researcherId);
    return hashMap;
  }

  @Override
  public List<String> getResearchersOfSimilarDomain(
    String rid,
    List<String> domains,
    List<String> partners
  ) {
    HashMap<String, Integer> hashMap = new HashMap<>();
    String sql =
      "select pr.rid from paper_researcher pr join paper_domain pd on pr.pid= pd.pid where pd.did in :domains";
    List<String> researchers = entityManager
      .createQuery(sql, String.class)
      .setParameter("domains", domains)
      .getResultList();
    for (String researcher : researchers) {
      Integer count = hashMap.get(researcher);
      hashMap.put(researcher, (count == null) ? 1 : count + 1);
    }
    researchers = new ArrayList<>(hashMap.keySet());
    researchers.remove(rid);
    for (String partner : partners) {
      researchers.remove(partner);
    }
    researchers.sort((o1, o2) -> hashMap.get(o2) - hashMap.get(o1));
    if (researchers.size() <= 5) {
      return researchers;
    } else {
      return researchers.subList(0, 5);
    }
  }

  @Override
  public List<String> getDomainsByResearcherId(String rid) {
    List<String> domains;
    String sql =
      "select pd.did from paper_domain pd join paper_researcher pr on pd.pid = pr.pid where pr.rid = :id";
    domains =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", rid)
        .getResultList();
    return domains;
  }

  @Override
  public List<String> getNearPartnersByRid(String researcherId) {
    List<String> papers = getPapersByRid(researcherId, 0, Integer.MAX_VALUE);
    HashMap<String, Integer> hashMap = getCoAuthorMap(researcherId, papers);
    List<String> partners = new ArrayList<>(hashMap.keySet());
    partners.sort((o1, o2) -> hashMap.get(o2) - hashMap.get(o1));
    if (partners.size() <= 5) {
      return partners;
    } else {
      return partners.subList(0, 5);
    }
  }

  @Override
  public List<JpaPaper> getPaperDateById(String researchId) {
    String sql =
      "select new paper(pr.paper.id,pr.paper.publicationDate) from paper_researcher pr where pr.rid = :rid";
    return entityManager
      .createQuery(sql, JpaPaper.class)
      .setParameter("rid", researchId)
      .getResultList();
  }
}
