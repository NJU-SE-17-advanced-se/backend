package org.njuse17advancedse.taskreviewerrecommendation.repository;

import com.google.common.collect.Lists;
import com.sun.istack.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPaperRepository implements PaperRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<String> getResearcherIdsFromReferences(
    List<String> references,
    List<String> partners
  ) {
    String sql =
      "select distinct pr.rid from paper_researcher pr where pr.pid in :references";
    List<String> researchers;
    researchers =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("references", references)
        .getResultList();
    researchers.removeAll(partners);
    return researchers;
  }

  @Override
  public List<String> getResearchersFromPublication(
    String paperJournal,
    int date,
    List<String> partners
  ) {
    String sql;
    sql =
      "select distinct pr.rid from paper_researcher pr  where pr.paper.publication.id in :journal and pr.paper.publicationDate between :start and :end";
    List<String> researchers;
    researchers =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("journal", paperJournal)
        .setParameter("start", date - 1)
        .setParameter("end", date + 1)
        .getResultList();
    researchers.removeAll(partners);
    return researchers;
  }

  @Override
  public List<String> getResearcherFromSimilarDomain(
    List<String> domains,
    int date,
    @Nullable List<String> partners
  ) {
    //        for(String domain:domains){
    //            String sql = "select count(d) from domain d where d.id = :id";
    //            if(Integer.parseInt(entityManager.createQuery(sql).setParameter("id",domain).getSingleResult().toString())==0){
    //                return Lists.newArrayList("no such domain: "+ domain);
    //            }
    //        }
    HashMap<String, Integer> hashMap = new HashMap<>();
    String sql =
      "select pr.rid from paper_researcher pr join paper_domain pd on pr.pid= pd.pid where pd.did in :domains and pd.paper.publicationDate between :start and :end";
    List<String> researchers;
    researchers =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("domains", domains)
        .setParameter("start", date - 1)
        .setParameter("end", date + 1)
        .getResultList();
    for (String researcher : researchers) {
      Integer count = hashMap.get(researcher);
      hashMap.put(researcher, (count == null) ? 1 : count + 1);
    }
    researchers = new ArrayList<>(hashMap.keySet());
    if (partners != null) {
      researchers.removeAll(partners);
    }
    researchers.sort((o1, o2) -> hashMap.get(o2) - hashMap.get(o1));
    return researchers;
  }

  @Override
  public List<String> getPastPartners(List<String> researcherIds) {
    String sql;
    sql =
      "select distinct pr.pid from paper_researcher pr where pr.rid in :rids";
    List<String> papers = entityManager
      .createQuery(sql, String.class)
      .setParameter("rids", researcherIds)
      .getResultList();
    sql =
      "select distinct pr.rid from paper_researcher pr where pr.pid in :papers";
    List<String> partners = entityManager
      .createQuery(sql, String.class)
      .setParameter("papers", papers)
      .getResultList();
    partners.removeAll(researcherIds);
    return partners;
  }

  @Override
  public List<String> getPartnersByAffiliation(List<String> researcherIds) {
    String sql;
    sql =
      "select distinct ra.aid from researcher_affiliation ra where ra.rid in :rids";
    List<String> affiliations = entityManager
      .createQuery(sql, String.class)
      .setParameter("rids", researcherIds)
      .getResultList();
    sql =
      "select distinct ra.rid from researcher_affiliation ra where ra.aid in :aff";
    List<String> partners;
    partners =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("aff", affiliations)
        .getResultList();
    partners.removeAll(researcherIds);
    return partners;
  }

  @Override
  public boolean containPublication(String paperJournal) {
    String sql;
    sql = "select count(p) from publication p where p.id =:id";
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("id", paperJournal)
        .getSingleResult()
        .toString()
    );
    return count != 0;
  }
}
