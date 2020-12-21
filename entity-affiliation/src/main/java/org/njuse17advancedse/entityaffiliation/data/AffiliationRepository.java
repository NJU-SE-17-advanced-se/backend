package org.njuse17advancedse.entityaffiliation.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.dto.IResult;
import org.springframework.stereotype.Repository;

@Repository
public class AffiliationRepository implements AllRepository {
  @PersistenceContext
  private EntityManager entityManager;

  public IAffiliation getAffiliationById(String id) {
    IAffiliation res = new IAffiliation();
    IAffiliationBasic iab = getAffiliationBasicInfoById(id);
    res.setId(id);
    res.setName(iab.getName());
    res.setDescription(iab.getDescription());
    res.setDomains(getAffiliationDomainsById(id));
    res.setResearchers(getAffiliationResearchersById(id));
    res.setPapers(getAffiliationPapersById(id));

    return res;
  }

  public IAffiliationBasic getAffiliationBasicInfoById(String id) {
    IAffiliationBasic iAffiliationBasic = new IAffiliationBasic();
    iAffiliationBasic.setId(id);
    String sql = "select name from affiliation where id=:id";
    String name = entityManager
      .createQuery(sql, String.class)
      .setParameter("id", id)
      .getSingleResult();
    iAffiliationBasic.setName(name);
    try {
      sql = "select description from affiliation where id=:id";
      iAffiliationBasic.setDescription(
        entityManager
          .createQuery(sql, String.class)
          .setParameter("id", id)
          .getSingleResult()
      );
    } catch (Exception ignored) {
      return iAffiliationBasic;
    }
    return iAffiliationBasic;
  }

  public boolean existsById(String id) {
    String sql = "select id from affiliation where id=:id";
    return (
      !entityManager
        .createQuery(sql, String.class)
        .setParameter("id", id)
        .getResultList()
        .isEmpty()
    );
  }

  public List<String> getAffiliationResearchersById(String id) {
    String sql =
      "select distinct rid from researcher_affiliation where aid=:id";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("id", id)
      .getResultList();
  }

  public List<String> getAffiliationPapersById(String id) {
    String sql =
      "select distinct pr.pid from paper_researcher pr join researcher_affiliation ra on pr.rid=ra.rid where ra.aid=:id";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("id", id)
      .getResultList();
  }

  public List<String> getAffiliationDomainsById(String id) {
    String sql =
      "select distinct pd.did from paper_domain pd join paper_researcher pr on pr.pid=pd.pid join researcher_affiliation ra on ra.rid=pr.rid where ra.aid=:id";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("id", id)
      .getResultList();
  }

  public IResult getAffiliationsByCond(String keyword, int page) {
    IResult result = new IResult();
    String sql = "select id from affiliation where lower(name) like :keyword";
    result.setIds(
      entityManager
        .createQuery(sql, String.class)
        .setParameter("keyword", "%" + keyword + "%")
        .setFirstResult((page - 1) * 10)
        .setMaxResults(10)
        .getResultList()
    );
    sql = "select count(id) from affiliation where lower(name) like :keyword";
    result.setCount(
      Integer.parseInt(
        entityManager
          .createQuery(sql)
          .setParameter("keyword", "%" + keyword + "%")
          .getSingleResult()
          .toString()
      )
    );
    return result;
  }
}
