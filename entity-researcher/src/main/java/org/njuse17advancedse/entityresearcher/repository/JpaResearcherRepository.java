package org.njuse17advancedse.entityresearcher.repository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.dto.ISearchResult;
import org.springframework.stereotype.Repository;

@Repository
public class JpaResearcherRepository implements ResearcherRepository {
  @PersistenceContext //并不会真正注入EntityManager，因为它不是线程安全的
  private EntityManager entityManager;

  @Override
  public IResearcher getResearcherById(String id) {
    String sql;
    String name;
    sql = "select r.name from researcher r where r.id = :rid";
    IResearcher iResearcher = new IResearcher();
    name =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("rid", id)
        .getSingleResult();
    sql =
      "select ra.affiliation.id from researcher_affiliation ra where ra.researcher.id = :rid";
    List<String> affiliations = entityManager
      .createQuery(sql, String.class)
      .setParameter("rid", id)
      .getResultList();
    sql =
      "select pr.paper.id from paper_researcher pr where pr.researcher.id = :rid";
    List<String> papers = entityManager
      .createQuery(sql, String.class)
      .setParameter("rid", id)
      .getResultList();
    List<String> domains;
    domains = getDomains(papers);
    iResearcher.setId(id);
    iResearcher.setName(name);
    iResearcher.setPapers(papers);
    iResearcher.setAffiliation(affiliations);
    iResearcher.setDomains(domains);
    return iResearcher;
  }

  /**
   * 根据论文id查询领域id列表
   * @param papers 论文id列表
   * @return 领域id列表
   */
  private List<String> getDomains(List<String> papers) {
    List<String> domains = new ArrayList<>();
    String sql;
    if (papers.size() > 0) {
      sql =
        "select distinct pd.domain.id from paper_domain pd where pd.paper.id in :papers";
      domains =
        entityManager
          .createQuery(sql, String.class)
          .setParameter("papers", papers)
          .getResultList();
    }
    return domains;
  }

  @Override
  public IResearcherBasic getResearcherBasic(String id) {
    String sql;
    String name;
    IResearcherBasic iResearcherBasic = new IResearcherBasic();

    sql = "select r.name from researcher r where r.id = :rid";
    name =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("rid", id)
        .getSingleResult();
    sql =
      "select distinct ra.affiliation.id from researcher_affiliation ra where ra.researcher.id = :rid";
    List<String> affiliations = entityManager
      .createQuery(sql, String.class)
      .setParameter("rid", id)
      .getResultList();
    sql =
      "select pr.paper.id from paper_researcher pr where pr.researcher.id = :rid order by pr.paper.citation desc";
    List<String> papers = entityManager
      .createQuery(sql, String.class)
      .setParameter("rid", id)
      .setMaxResults(5)
      .getResultList();
    List<String> domains = getDomains(papers);
    iResearcherBasic.setId(id);
    iResearcherBasic.setName(name);
    iResearcherBasic.setPapers(papers);
    iResearcherBasic.setAffiliation(affiliations);
    iResearcherBasic.setDomains(domains);
    return iResearcherBasic;
  }

  @Override
  public List<String> findPapers(String id, int startDate, int endDate) {
    String sql;
    List<String> papers;
    sql =
      "select distinct pr.paper.id from paper_researcher pr where pr.researcher.id = :rid and pr.paper.publicationDate between :start and :end";
    papers =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("rid", id)
        .setParameter("start", startDate)
        .setParameter("end", endDate)
        .getResultList();
    return papers;
  }

  @Override
  public List<String> findDomains(String id, int startDate, int endDate) {
    String sql;
    List<String> domains;
    sql =
      "select distinct pd.did from paper_domain pd join paper_researcher pr on pr.pid = pd.pid where pr.rid = :rid and pd.paper.publicationDate between :start and :end";
    domains =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("rid", id)
        .setParameter("start", startDate)
        .setParameter("end", endDate)
        .getResultList();
    return domains;
  }

  @Override
  public List<String> findAffiliations(String rid, int startDate, int endDate) {
    String sql;
    List<String> affiliations;
    sql =
      "select distinct ra.affiliation.id from researcher_affiliation ra where ra.researcher.id = :rid and ra.year between :a and :b";
    affiliations =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("rid", rid)
        .setParameter("a", startDate)
        .setParameter("b", endDate)
        .getResultList();
    return affiliations;
  }

  @Override
  public ISearchResult searchByCond(String keyword, int page) {
    ISearchResult iSearchResult = new ISearchResult();
    String sql;
    keyword = keyword.toLowerCase();
    int count = 0;
    List<String> result = new ArrayList<>();
    if (page >= 1) {
      sql = "select r.id from researcher r where lower(r.name) like :keyword";
      result =
        entityManager
          .createQuery(sql, String.class)
          .setParameter("keyword", "%" + keyword + "%")
          .setFirstResult((page - 1) * 10)
          .setMaxResults(10)
          .getResultList();
      sql =
        "select count(r) from researcher r where lower(r.name) like :keyword";
      count =
        Integer.parseInt(
          entityManager
            .createQuery(sql)
            .setParameter("keyword", "%" + keyword + "%")
            .getSingleResult()
            .toString()
        );
    }
    iSearchResult.setCount(count);
    iSearchResult.setResult(result);
    return iSearchResult;
  }

  @Override
  public boolean containResearcher(String id) {
    String sql = "select count(r) from researcher r where r.id = :id";
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("id", id)
        .getSingleResult()
        .toString()
    );
    return count != 0;
  }
}
