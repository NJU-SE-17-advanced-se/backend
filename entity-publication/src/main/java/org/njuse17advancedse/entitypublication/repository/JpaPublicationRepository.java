package org.njuse17advancedse.entitypublication.repository;

import com.sun.istack.Nullable;
import java.util.List;
import javax.persistence.EntityManager;
import org.njuse17advancedse.entitypublication.entity.JpaPublication;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPublicationRepository implements PublicationRepository {
  private final EntityManager entityManager;

  public JpaPublicationRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public JpaPublication findPublicationById(String id) {
    return entityManager.find(JpaPublication.class, id);
  }

  @Override
  public List<String> findPublications(
    @Nullable String name,
    @Nullable String start,
    @Nullable String end
  ) {
    int startDate = 0;
    int endDate = 9999;
    String sql;
    List<String> publications;
    if (name == null) {
      if (start != null) {
        startDate = Integer.parseInt(start);
      }
      if (end != null) {
        endDate = Integer.parseInt(end);
      }
      sql =
        "select p.id from publication p where p.publicationDate between :a and :b";
      publications =
        entityManager
          .createQuery(sql, String.class)
          .setParameter("a", startDate)
          .setParameter("b", endDate)
          .getResultList();
    } else {
      if (start != null) {
        startDate = Integer.parseInt(start);
      }
      if (end != null) {
        endDate = Integer.parseInt(end);
      }
      sql =
        "select p.id from publication p where p.name like :p_name and p.publicationDate between :a and :b";
      publications =
        entityManager
          .createQuery(sql, String.class)
          .setParameter("p_name", "%" + name + "%")
          .setParameter("a", startDate)
          .setParameter("b", endDate)
          .getResultList();
    }
    return publications;
  }
}
