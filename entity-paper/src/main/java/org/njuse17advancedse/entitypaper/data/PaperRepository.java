package org.njuse17advancedse.entitypaper.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.njuse17advancedse.entitypaper.entity.JpaPaper;
import org.springframework.stereotype.Repository;

@Repository
public class PaperRepository implements AllRepository {
  @PersistenceContext //并不会真正注入EntityManager，因为它不是线程安全的
  private EntityManager entityManager;

  public boolean existsById(String id) {
    String sql = "select id from paper where id=:id";
    return (
      !entityManager
        .createQuery(sql, String.class)
        .setParameter("id", id)
        .getResultList()
        .isEmpty()
    );
  }

  public List<String> getDomains(String pid) {
    String sql = "select did from paper_domain where pid=:pid";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("pid", pid)
      .getResultList();
  }

  public IPaper getIPaper(String paperId) {
    JpaPaper p = entityManager.find(JpaPaper.class, paperId);
    IPaper paper = new IPaper();
    paper.setId(paperId);
    paper.setTitle(p.getTitle());
    paper.setAbs(p.getAbs());
    paper.setCitations(p.getCitation());
    paper.setPublication(p.getPublication().getId());
    paper.setPublicationDate(p.getPublicationDate());
    paper.setLink(p.getLink());
    String sql = "select pd.did from paper_domain pd where pd.pid =:id";
    List<String> domains = entityManager
      .createQuery(sql, String.class)
      .setParameter("id", paperId)
      .getResultList();
    paper.setDomains(domains);
    sql = "select pr.rid from paper_researcher pr where pr.pid=:id";
    paper.setResearchers(
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", paperId)
        .getResultList()
    );
    sql = "select pr.rid from paper_reference pr where pr.pid=:id";
    paper.setReferences(
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", paperId)
        .getResultList()
    );
    return paper;
  }

  public IPaperBasic getPaperBasic(String paperId) {
    IPaperBasic paper = new IPaperBasic();
    JpaPaper p = entityManager.find(JpaPaper.class, paperId);
    paper.setId(paperId);
    paper.setTitle(p.getTitle());
    paper.setAbs(p.getAbs());
    paper.setCitations(p.getCitation());
    paper.setPublication(p.getPublication().getId());
    paper.setPublicationDate(p.getPublicationDate());
    String sql = "select pr.rid from paper_researcher pr where pr.pid=:id";
    paper.setResearchers(
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", paperId)
        .getResultList()
    );
    return paper;
  }

  public List<String> getCitations(String paperId) {
    String sql = "select pid from paper_reference where rid=:paperId";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("paperId", paperId)
      .getResultList();
  }

  public IResult getPaperByCond(String keyword, int page) {
    String sql =
      "select p.id from paper p where ((lower(p.title) like :keyword10) or (lower(p.abs)) like :keyword10)";
    List<String> result = entityManager
      .createQuery(sql, String.class)
      .setParameter("keyword10", "%" + keyword + "%")
      .setFirstResult((page - 1) * 10)
      .setMaxResults(10)
      .getResultList();
    sql =
      "select count(p) from paper p where ((lower(p.title) like :keyword12) or (lower(p.abs)) like :keyword12)";
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("keyword12", "%" + keyword + "%")
        .getSingleResult()
        .toString()
    );
    return new IResult(result, count);
  }

  public IResult getPaperByCond(
    String keyword,
    String year,
    boolean type,
    int page
  ) {
    String sql;
    if (type) {
      sql =
        "select p.id from paper p where ((lower(p.title) like :keyword3) or (lower(p.abs) like :keyword3)) and p.publicationDate>=:year";
    } else {
      sql =
        "select p.id from paper p where ((lower(p.title) like :keyword3) or (lower(p.abs) like :keyword3)) and p.publicationDate<=:year";
    }
    List<String> result = entityManager
      .createQuery(sql, String.class)
      .setParameter("keyword3", "%" + keyword + "%")
      .setParameter("year", year)
      .setFirstResult((page - 1) * 10)
      .setMaxResults(10)
      .getResultList();
    if (type) {
      sql =
        "select count(p) from paper p where ((lower(p.title) like :keyword4) or (lower(p.abs) like :keyword4)) and p.publicationDate>=:year";
    } else {
      sql =
        "select count(p) from paper p where ((lower(p.title) like :keyword4) or (lower(p.abs) like :keyword4)) and p.publicationDate<=:year";
    }
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("keyword4", "%" + keyword + "%")
        .setParameter("year", year)
        .getSingleResult()
        .toString()
    );
    return new IResult(result, count);
  }

  public IResult getPaperByCond(
    String keyword,
    String startYear,
    String endYear,
    int page
  ) {
    String sql =
      "select p.id from paper p where ((lower(p.title) like :keyword1) or (lower(p.abs) like :keyword2)) and p.publicationDate>=:start and p.publicationDate<=:endyear";
    List<String> result = entityManager
      .createQuery(sql, String.class)
      .setParameter("keyword1", "%" + keyword + "%")
      .setParameter("keyword2", "%" + keyword + "%")
      .setParameter("start", startYear)
      .setParameter("endyear", endYear)
      .setFirstResult((page - 1) * 10)
      .setMaxResults(10)
      .getResultList();
    sql =
      "select count(p) from paper p where ((lower(p.title) like :keyword1) or (lower(p.abs) like :keyword2)) and p.publicationDate>=:start and p.publicationDate<=:endyear";
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("keyword1", "%" + keyword + "%")
        .setParameter("keyword2", "%" + keyword + "%")
        .setParameter("start", startYear)
        .setParameter("endyear", endYear)
        .getSingleResult()
        .toString()
    );
    return new IResult(result, count);
  }
}
