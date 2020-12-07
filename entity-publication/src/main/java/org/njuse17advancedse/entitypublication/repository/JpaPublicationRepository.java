package org.njuse17advancedse.entitypublication.repository;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPublicationRepository implements PublicationRepository {
  private final EntityManager entityManager;

  public JpaPublicationRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public IPublication findPublication(String id) {
    IPublication iPublication = new IPublication();
    String sql = "select p.name from publication p where p.id=:id";
    String name = entityManager
      .createQuery(sql, String.class)
      .setParameter("id", id)
      .getSingleResult();
    sql = "select p.publicationDate from publication p where p.id=:id";
    Integer publicationDate = entityManager
      .createQuery(sql, Integer.class)
      .setParameter("id", id)
      .getSingleResult();
    sql = "select p.impact from publication p where p.id=:id";
    Double impact = entityManager
      .createQuery(sql, Double.class)
      .setParameter("id", id)
      .getSingleResult();
    iPublication.setId(id);
    iPublication.setName(name);
    iPublication.setPublicationDate(publicationDate + "");
    iPublication.setImpact(impact);
    sql = "select p.id from paper p where p.publication.id = :id";
    List<String> papers = entityManager
      .createQuery(sql, String.class)
      .setParameter("id", id)
      .getResultList();
    if (papers != null) iPublication.setPapers(papers);
    return iPublication;
  }

  @Override
  public IPublicationBasic findPublicationBasic(String id) {
    IPublicationBasic iPublicationBasic = new IPublicationBasic();
    String sql;
    String name;
    sql = "select p.name from publication p where p.id=:id";
    name =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", id)
        .getSingleResult();
    sql = "select p.publicationDate from publication p where p.id=:id";
    Integer publicationDate = entityManager
      .createQuery(sql, Integer.class)
      .setParameter("id", id)
      .getSingleResult();
    iPublicationBasic.setId(id);
    iPublicationBasic.setName(name);
    iPublicationBasic.setPublicationDate(publicationDate + "");
    return iPublicationBasic;
  }

  @Override
  public List<String> getPapers(String id, int startDate, int endDate) {
    String sql;
    List<String> papers;
    sql =
      "select p.id from paper p where p.publication.id = :id and p.publicationDate between :start and :end";
    papers =
      entityManager
        .createQuery(sql, String.class)
        .setParameter("id", id)
        .setParameter("start", startDate)
        .setParameter("end", endDate)
        .getResultList();
    return papers;
  }

  @Override
  public ISearchResult searchByCond(
    String keyword,
    int startDate,
    int endDate,
    int page
  ) {
    ISearchResult iSearchResult = new ISearchResult();
    String sql;
    keyword = keyword.toLowerCase();
    int count = 0;
    List<String> result = new ArrayList<>();
    if (page >= 1) {
      sql =
        "select p.id from publication p where lower(p.name) like :keyword and p.publicationDate between :start and :end";
      result =
        entityManager
          .createQuery(sql, String.class)
          .setParameter("keyword", "%" + keyword + "%")
          .setParameter("start", startDate)
          .setParameter("end", endDate)
          .setFirstResult((page - 1) * 10)
          .setMaxResults(10)
          .getResultList();
      sql =
        "select count(p) from publication p where lower(p.name) like :keyword and p.publicationDate between :start and :end";
      count =
        Integer.parseInt(
          entityManager
            .createQuery(sql)
            .setParameter("keyword", "%" + keyword + "%")
            .setParameter("start", startDate)
            .setParameter("end", endDate)
            .getSingleResult()
            .toString()
        );
    }
    iSearchResult.setCount(count);
    iSearchResult.setResult(result);
    return iSearchResult;
  }

  @Override
  public boolean containPublication(String id) {
    String sql = "select count(p) from publication p where p.id = :id";
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
