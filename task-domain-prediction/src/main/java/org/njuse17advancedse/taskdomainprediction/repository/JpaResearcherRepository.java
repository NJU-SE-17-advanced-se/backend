package org.njuse17advancedse.taskdomainprediction.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class JpaResearcherRepository implements ResearcherRepository {
  @PersistenceContext //并不会真正注入EntityManager，因为它不是线程安全的
  private EntityManager entityManager;

  @Override
  public boolean containResearcher(String rid) {
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
  public List<String> getPastDomains(String rid) {
    String sql;
    Calendar date = Calendar.getInstance();
    int year = date.get(Calendar.YEAR);
    List<String> domains;
    sql =
      "select pd.did from paper_domain pd join paper_researcher pr on pr.pid = pd.pid where pr.rid = :rid and pd.paper.publicationDate between :start and :end";
    domains =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("rid", rid)
        .setParameter("start", year - 2)
        .setParameter("end", year)
        .getResultList();
    if (domains.size() == 0) {
      sql =
        "select pd.did from paper_domain pd join paper_researcher pr on pr.pid = pd.pid where pr.rid = :rid";
      domains =
        entityManager
          .createQuery(sql, String.class)
          .setParameter("rid", rid)
          .getResultList();
    }
    HashMap<String, Integer> hashMap = new HashMap<>();
    for (String domain : domains) {
      Integer count = hashMap.get(domain);
      hashMap.put(domain, (count == null) ? 1 : count + 1);
    }
    domains = new ArrayList<>(hashMap.keySet());
    domains.sort((o1, o2) -> hashMap.get(o2) - hashMap.get(o1));
    if (domains.size() > 10) {
      domains = domains.subList(0, 10);
    }
    return domains;
  }

  @Override
  public Double getDomainImpact(String domain) {
    String sql;
    sql = "select pd.paper.citation from paper_domain pd where pd.did = :id";
    List<Integer> impacts;
    impacts =
      entityManager
        .createQuery(sql, Integer.class)
        .setParameter("id", domain)
        .getResultList();
    double sum = 0.0;
    for (int impact : impacts) {
      sum += impact;
    }
    return sum;
  }
}
