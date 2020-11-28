package org.njuse17advancedse.taskcitationanalysis.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRepository implements CitationAnalysisRepository {
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

  //获取一篇论文引用了哪些论文
  public List<String> getQuotedPapersByPaperId(String paperId) {
    String sql = "select rid from paper_reference where pid=:paperId";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("paperId", paperId)
      .getResultList();
  }

  //获取一篇论文被哪些论文引用
  public List<String> getQuotingPapersByPaperId(String paperId) {
    String sql = "select pid from paper_reference where rid=:paperId";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("paperId", paperId)
      .getResultList();
  }

  //获取一篇论文引用了哪些学者
  public List<String> getPaperQuotedResearcher(String paperId) {
    String sql =
      "select pre.rid from paper_reference prf join paper_researcher pre on prf.rid=pre.pid where prf.pid=:paperId";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("paperId", paperId)
      .getResultList();
  }

  //获取一篇论文被哪些学者引用
  public List<String> getPaperQuotingResearcher(String paperId) {
    String sql =
      "select pre.rid from paper_reference prf join paper_researcher pre on prf.pid=pre.pid where prf.rid=:paperId";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("paperId", paperId)
      .getResultList();
  }

  //获取一个学者的论文列表
  public List<String> getPapersByResearcherId(String researcherId) {
    String sql = "select pid from paper_researcher where rid=:researcherId";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("researcherId", researcherId)
      .getResultList();
  }

  public Map<String, List<String>> getResearcherPaperQuotedPapers(
    String researcherId
  ) {
    String sql =
      "select concat(ps1.pid,' ',pf1.rid) from paper_researcher ps1 join paper_reference pf1 on ps1.pid=pf1.pid where ps1.rid=:researcherId";
    return getStringListMap(researcherId, sql);
  }

  public Map<String, List<String>> getResearcherPaperQuotingPapers(
    String researcherId
  ) {
    String sql =
      "select concat(ps1.pid,' ',pf1.pid) from paper_researcher ps1 join paper_reference pf1 on ps1.pid=pf1.rid where ps1.rid=:researcherId";
    return getStringListMap(researcherId, sql);
  }

  public Map<String, List<String>> getResearcherPaperQuotingResearchers(
    String researcherId
  ) {
    String sql =
      "select concat(ps1.pid,' ',ps2.rid) from paper_researcher ps1 join paper_reference pf1 on ps1.pid=pf1.rid join paper_researcher ps2 on pf1.pid = ps2.pid where ps1.rid=:researcherId";
    return getStringListMap(researcherId, sql);
  }

  public Map<String, List<String>> getResearcherPaperQuotedResearchers(
    String researcherId
  ) {
    String sql =
      "select concat(ps1.pid,' ',ps2.rid) from paper_researcher ps1 join paper_reference pf1 on ps1.pid=pf1.pid join paper_researcher ps2 on pf1.rid = ps2.pid where ps1.rid=:researcherId";
    return getStringListMap(researcherId, sql);
  }

  //某学者引用了哪些学者
  public List<String> getResearcherQuotedResearcher(String researcherId) {
    //        String sql="select ps1.rid from paper_researcher ps1 join paper_reference pf on ps1.pid=pf.rid join paper_researcher ps2 on pf.pid=ps2.rid where ps1.rid=:researcherId";
    String sql =
      "select rid from paper_researcher where pid in (select rid from paper_reference where pid in (select pid from paper_researcher where rid=:researcherId))";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("researcherId", researcherId)
      .getResultList();
  }

  //某学者被哪些学者引用
  public List<String> getResearcherQuotingResearcher(String researcherId) {
    //        String sql="select ps2.rid from paper_researcher ps1 join paper_reference pf on ps1.pid=pf.rid join paper_researcher ps2 on pf.pid=ps2.rid where ps1.rid=:researcherId";
    String sql =
      "select rid from paper_researcher where pid in (select pid from paper_reference where rid in (select pid from paper_researcher where rid=:researcherId))";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("researcherId", researcherId)
      .getResultList();
  }

  private Map<String, List<String>> getStringListMap(
    String researcherId,
    String sql
  ) {
    List<String> queryRes = entityManager
      .createQuery(sql, String.class)
      .setParameter("researcherId", researcherId)
      .getResultList();
    Map<String, List<String>> res = new HashMap<>();
    for (String data : queryRes) {
      String[] strs = data.split(" ");
      put(res, strs[0], strs[1]);
    }
    List<String> papers = getPapersByResearcherId(researcherId);
    for (String pid : papers) {
      put(res, pid);
    }
    return res;
  }

  private void put(Map<String, List<String>> map, String key, String value) {
    if (map.containsKey(key)) {
      map.get(key).add(value);
      return;
    }
    List<String> list = new ArrayList<>();
    list.add(value);
    map.put(key, list);
  }

  private void put(Map<String, List<String>> map, String key) {
    if (!map.containsKey(key)) {
      map.put(key, new ArrayList<>());
    }
  }
}
