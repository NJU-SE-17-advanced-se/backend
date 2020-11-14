package org.njuse17advancedse.entityresearcher.repository;

import com.sun.istack.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
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
    sql = "select r.name from researcher r where r.id = :rid";
    IResearcher iResearcher = new IResearcher();
    String name = entityManager
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
    List<String> domains = new ArrayList<>();
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
    HashSet<String> hashSet = new HashSet<>();
    String sql;
    if (papers != null) {
      for (String pid : papers) {
        sql =
          "select pd.domain.id from paper_domain pd where pd.paper.id = :pid";
        List<String> domain = entityManager
          .createQuery(sql, String.class)
          .setParameter("pid", pid)
          .getResultList();
        hashSet.addAll(domain);
      }
      domains = new ArrayList<>(hashSet);
    }
    return domains;
  }

  @Override
  public IResearcherBasic getResearcherBasic(String id) {
    String sql;
    sql = "select r.name from researcher r where r.id = :rid";
    IResearcherBasic iResearcherBasic = new IResearcherBasic();
    String name = entityManager
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
  public List<String> findPapers(String id, String start, String end) {
    int startDate = 0;
    int endDate = 9999;
    String sql;
    List<String> papers = new ArrayList<>();
    if (start != null) {
      startDate = Integer.parseInt(start);
    }
    if (end != null) {
      endDate = Integer.parseInt(end);
    }
    try {
      sql =
        "select pr.paper.id from paper_researcher pr where pr.researcher.id = :rid and pr.paper.publicationDate between :start and :end";
      papers =
        entityManager
          .createQuery(sql, String.class)
          .setParameter("rid", id)
          .setParameter("start", startDate)
          .setParameter("end", endDate)
          .getResultList();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return papers;
  }

  @Override
  public List<String> findDomains(String id, String start, String end) {
    int startDate = 0;
    int endDate = 9999;
    String sql;
    List<String> domains = new ArrayList<>();
    if (start != null) {
      startDate = Integer.parseInt(start);
    }
    if (end != null) {
      endDate = Integer.parseInt(end);
    }
    try {
      sql =
        "select pr.paper.id from paper_researcher pr where pr.researcher.id = :rid and pr.paper.publicationDate between :start and :end";
      List<String> papers = entityManager
        .createQuery(sql, String.class)
        .setParameter("rid", id)
        .setParameter("start", startDate)
        .setParameter("end", endDate)
        .getResultList();
      domains = getDomains(papers);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return domains;
  }

  @Override
  public List<String> findAffiliations(
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

  @Override
  public ISearchResult searchByCond(String keyword, int page) {
    ISearchResult iSearchResult = new ISearchResult();
    String sql;
    keyword = keyword.toLowerCase();
    int count = 0;
    List<String> result = new ArrayList<>();
    if (page >= 1) {
      try {
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
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    iSearchResult.setCount(count);
    iSearchResult.setResult(result);
    return iSearchResult;
  }
}
