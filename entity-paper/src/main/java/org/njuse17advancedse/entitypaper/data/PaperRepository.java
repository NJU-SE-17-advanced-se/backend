package org.njuse17advancedse.entitypaper.data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.var;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.njuse17advancedse.entitypaper.entity.JpaDomain;
import org.njuse17advancedse.entitypaper.entity.JpaPaper;
import org.njuse17advancedse.entitypaper.entity.JpaResearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaperRepository implements AllRepository {
  private static final String separator = "@#";

  //    @Autowired
  //    private JpaPaperRepository repository;

  @PersistenceContext //并不会真正注入EntityManager，因为它不是线程安全的
  private EntityManager entityManager;

  public boolean existsById(String id) {
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

  public List<String> getDomains(String pid) {
    String sql = "select did from paper_domain where pid=:pid";
    return entityManager
      .createQuery(sql, String.class)
      .setParameter("pid", pid)
      .getResultList();
  }

  public IPaper getIPaper(String paperId) {
    IPaperBasic paperBasic = getPaperBasic(paperId);
    IPaper res = new IPaper();
    res.setId(paperId);
    res.setTitle(paperBasic.getTitle());
    res.setAbs(paperBasic.getAbs());
    res.setCitations(paperBasic.getCitations());
    res.setPublication(paperBasic.getPublication());
    res.setPublicationDate(paperBasic.getPublicationDate());
    res.setResearchers(paperBasic.getResearchers());

    res.setDomains(getDomains(paperId));

    String sql = "select rid from paper_reference where pid=:paperId";
    res.setReferences(
      entityManager
        .createQuery(sql)
        .setParameter("paperId", paperId)
        .getResultList()
    );

    try {
      sql = "select link from paper where id=:paperId";
      res.setLink(
        entityManager
          .createQuery(sql, String.class)
          .setParameter("paperId", paperId)
          .getSingleResult()
      );
    } catch (Exception e) {}
    return res;
    //        String sql="select OBJECT(p) from paper p where p.id=:paperId";
    ////        JpaPaper p=entityManager.createQuery(sql,JpaPaper.class).setParameter("paperId",paperId).getSingleResult();
    //        var tmp=repository.findById(paperId);
    //                JpaPaper p=tmp.get();
    //        IPaper paper=new IPaper();
    //        paper.setId(paperId);
    //        paper.setTitle(p.getTitle());
    //        paper.setAbs(p.getAbs());
    //        paper.setCitations(p.getCitation());
    //        paper.setPublication(p.getPublication().getId());
    //        paper.setPublicationDate(p.getPublicationDate());
    //        paper.setLink(p.getLink());
    //        List<String> domains=new ArrayList<>();
    //        for(JpaDomain domain:p.getDomains())
    //            domains.add(domain.getId());
    //        paper.setDomains(domains);
    //        List<String> researchers=new ArrayList<>();
    //        for(JpaResearcher researcher:p.getResearchers())
    //            domains.add(researcher.getId());
    //        paper.setResearchers(researchers);
    //        sql="select rid from paper_reference where pid=:paperId";
    //        paper.setReferences(entityManager.createQuery(sql).setParameter("paperId",paperId).getResultList());
    //        return paper;
  }

  public IPaperBasic getPaperBasic(String paperId) {
    IPaperBasic iPaperBasic = new IPaperBasic();
    //        String s="select publication_date from paper_where id='s'";
    String sql =
      "select concat(p.title,'@#',p.abs,'@#',p.publication,'@#',p.publicationDate) as res from paper p where p.id=:paperId";
    String res = entityManager
      .createQuery(sql, String.class)
      .setParameter("paperId", paperId)
      .getSingleResult();
    String[] strs = res.split(separator);
    iPaperBasic.setId(paperId);
    iPaperBasic.setTitle(strs[0]);
    iPaperBasic.setAbs(strs[1]);
    iPaperBasic.setPublication(strs[2]);
    iPaperBasic.setPublicationDate(strs[3]);

    sql = "select citation from paper where id=:paperId";
    iPaperBasic.setCitations(
      Integer.parseInt(
        entityManager
          .createQuery(sql)
          .setParameter("paperId", paperId)
          .getSingleResult()
          .toString()
      )
    );

    sql = "select rid from paper_researcher where pid=:paperId";
    iPaperBasic.setResearchers(
      entityManager
        .createQuery(sql, String.class)
        .setParameter("paperId", paperId)
        .getResultList()
    );

    return iPaperBasic;
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
      "select p.id from paper p where ((lower(p.title) like :keyword) or (lower(p.abs)) like :keyword)";
    List<String> result = entityManager
      .createQuery(sql, String.class)
      .setParameter("keyword", "%" + keyword + "%")
      .setFirstResult((page - 1) * 10)
      .setMaxResults(10)
      .getResultList();
    sql =
      "select count(p) from paper p where ((lower(p.title) like :keyword) or (lower(p.abs)) like :keyword)";
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("keyword", "%" + keyword + "%")
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
    String date = "and ";
    if (type) {
      date += "p.publicationDate>=:year";
    } else {
      date += "p.publicationDate<=:year";
    }
    String sql =
      "select p.id from paper p where ((lower(p.title) like :keyword) or (lower(p.abs) like :keyword)) " +
      date;
    List<String> result = entityManager
      .createQuery(sql, String.class)
      .setParameter("keyword", "%" + keyword + "%")
      .setParameter("year", year)
      .setFirstResult((page - 1) * 10)
      .setMaxResults(10)
      .getResultList();
    sql =
      "select count(p) from paper p where ((lower(p.title) like :keyword) or (lower(p.abs) like :keyword)) " +
      date;
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("keyword", "%" + keyword + "%")
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
      "select p.id from paper p where ((lower(p.title) like :keyword1) or (lower(p.abs) like :keyword2)) and p.publicationDate>=:start and p.publicationDate<=:endy";
    List<String> result = entityManager
      .createQuery(sql, String.class)
      .setParameter("keyword1", "%" + keyword + "%")
      .setParameter("keyword2", "%" + keyword + "%")
      .setParameter("start", startYear)
      .setParameter("endy", endYear)
      .setFirstResult((page - 1) * 10)
      .setMaxResults(10)
      .getResultList();
    sql =
      "select count(p) from paper p where ((lower(p.title) like :keyword1) or (lower(p.abs) like :keyword2)) and p.publicationDate>=:start and p.publicationDate<=:endy";
    int count = Integer.parseInt(
      entityManager
        .createQuery(sql)
        .setParameter("keyword1", "%" + keyword + "%")
        .setParameter("keyword2", "%" + keyword + "%")
        .setParameter("start", startYear)
        .setParameter("endy", endYear)
        .getSingleResult()
        .toString()
    );
    return new IResult(result, count);
  }
}
