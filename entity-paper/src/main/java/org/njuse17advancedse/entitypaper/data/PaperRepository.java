package org.njuse17advancedse.entitypaper.data;

import io.swagger.models.auth.In;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.var;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.njuse17advancedse.entitypaper.entity.JpaDomain;
import org.njuse17advancedse.entitypaper.entity.JpaPaper;
import org.njuse17advancedse.entitypaper.entity.JpaPublication;
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
    //      IPaper res = new IPaper();
    //      String sql =
    //              "select concat(p.title,'@#',p.abs,'@#',p.publication,'@#',p.publicationDate,'@#', IFNULL (p.link,'SKTFaker'),'@#',p.citation) as res from paper p where p.id=:paperId";
    //      String tmp = entityManager
    //              .createQuery(sql, String.class)
    //              .setParameter("paperId", paperId)
    //              .getSingleResult();
    //      String[] strs = tmp.split(separator);
    //      res.setId(paperId);
    //      res.setTitle(strs[0]);
    //      res.setAbs(strs[1]);
    //      res.setPublication(strs[2]);
    //      res.setPublicationDate(strs[3]);
    //      res.setCitations(Integer.parseInt(strs[5]));
    //    res.setLink(strs[4].equals("SKTFaker")?null:strs[4]);
    //      String sql="select title from paper where id=:paperId";
    //      res.setId(paperId);
    //      res.setTitle(entityManager.createQuery(sql,String.class).setParameter("paperId",paperId).getSingleResult());
    //      sql="select p.abs from paper p where p.id=:paperId";
    //      res.setAbs(entityManager.createQuery(sql,String.class).setParameter("paperId",paperId).getSingleResult());
    //      sql="select p.publication from paper p where p.id=:paperId";
    //      res.setPublication(entityManager.createQuery(sql, JpaPublication.class).setParameter("paperId",paperId).getSingleResult().getId());
    //      sql="select p.publicationDate from paper p where p.id=:paperId";
    //      res.setPublicationDate(entityManager.createQuery(sql,String.class).setParameter("paperId",paperId).getSingleResult());
    //      sql="select p.link from paper p where p.id=:paperId";
    //      res.setLink(entityManager.createQuery(sql,String.class).setParameter("paperId",paperId).getSingleResult());
    //      sql="select p.citation from paper p where p.id=:paperId";
    //      res.setCitations(Integer.parseInt(entityManager.createQuery(sql).setParameter("paperId",paperId).getSingleResult().toString()));
    //
    //
    //      sql = "select rid from paper_researcher where pid=:paperId";
    //      res.setResearchers(
    //              entityManager
    //                      .createQuery(sql, String.class)
    //                      .setParameter("paperId", paperId)
    //                      .getResultList()
    //      );
    //
    //    res.setDomains(getDomains(paperId));
    //
    //    sql = "select rid from paper_reference where pid=:paperId";
    //    res.setReferences(
    //      entityManager
    //        .createQuery(sql,String.class)
    //        .setParameter("apaperId", paperId)
    //        .getResultList()
    //    );
    //
    //
    //    return res;
    //        String sql="select OBJECT(p) from paper p where p.id=:paperId";
    ////        JpaPaper p=entityManager.createQuery(sql,JpaPaper.class).setParameter("paperId",paperId).getSingleResult();
    //        var tmp=repository.findById(paperId);
    //                JpaPaper p=tmp.get()
    //                ;
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
    //            paper.setDomains(p.getDomains().stream().map(JpaDomain::getId).collect(Collectors.toList()));
    //            paper.setResearchers(p.getResearchers().stream().map(JpaResearcher::getId).collect(Collectors.toList()));
    //            paper.setReferences(p.getReferences().stream().map(JpaPaper::getId).collect(Collectors.toList()));
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
