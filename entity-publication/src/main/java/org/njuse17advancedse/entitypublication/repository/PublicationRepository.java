package org.njuse17advancedse.entitypublication.repository;

import com.sun.istack.Nullable;
import java.util.List;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;

public interface PublicationRepository {
  IPublication findPublication(String id);

  IPublicationBasic findPublicationBasic(String id);

  List<String> getPapers(
    String id,
    @Nullable String start,
    @Nullable String end
  );

  List<String> findPublications(
    @Nullable String name,
    @Nullable String start,
    @Nullable String end
  );

  ISearchResult searchByCond(
    String keyword,
    @Nullable String start,
    @Nullable String end,
    int page
  );
}
