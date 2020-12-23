package org.njuse17advancedse.entitydomain.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;
import org.springframework.stereotype.Repository;

@Repository
public class DomainRepository implements AllRepository {
  @PersistenceContext
  private EntityManager entityManager;

  public boolean existsById(String domainId) {
    String sql = "select id from domain where id=:id";
    return (
      !entityManager
        .createQuery(sql, String.class)
        .setParameter("id", domainId)
        .getResultList()
        .isEmpty()
    );
  }

  public IDomain getDomain(String domainId) {
    IDomainBasic basic = getDomainBasic(domainId);
    IDomain res = new IDomain();
    res.setId(domainId);
    res.setName(basic.getName());
    res.setPapers(getPapers(domainId));
    res.setResearchers(getResearchers(domainId));
    return res;
  }

  public IDomainBasic getDomainBasic(String domainId) {
    IDomainBasic res = new IDomainBasic();
    String sql = "select name from domain where id=:id";
    res.setName(
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", domainId)
        .getSingleResult()
    );
    res.setId(domainId);
    return res;
  }

  public List<String> getPapers(String domainId) {
    String sql = "select distinct pid from paper_domain where did=:domainId";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("domainId", domainId)
      .getResultList();
  }

  public List<String> getResearchers(String domainId) {
    String sql =
      "select distinct pr.rid from paper_researcher pr join paper_domain pd on pr.pid=pd.pid where pd.did=:domainId";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("domainId", domainId)
      .getResultList();
  }

  public IResult getDomainsByCond(String keyword, int page) {
    IResult result = new IResult();
    String sql = "select id from domain where lower(name) like :keyword";
    result.setIds(
      entityManager
        .createQuery(sql, String.class)
        .setParameter("keyword", "%" + keyword + "%")
        .setFirstResult((page - 1) * 10)
        .setMaxResults(10)
        .getResultList()
    );
    sql = "select count(id) from domain where lower(name) like :keyword";
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
