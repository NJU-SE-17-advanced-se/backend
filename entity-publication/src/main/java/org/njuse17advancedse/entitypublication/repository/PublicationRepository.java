package org.njuse17advancedse.entitypublication.repository;

import com.sun.istack.Nullable;
import java.util.List;
import org.njuse17advancedse.entitypublication.entity.JpaPublication;

public interface PublicationRepository {
  JpaPublication findPublicationById(String id);

  List<String> findPublications(
    @Nullable String name,
    @Nullable String start,
    @Nullable String end
  );
}
