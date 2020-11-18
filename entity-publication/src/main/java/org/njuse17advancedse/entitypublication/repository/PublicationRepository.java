package org.njuse17advancedse.entitypublication.repository;

import java.util.List;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;

public interface PublicationRepository {
  IPublication findPublication(String id);

  IPublicationBasic findPublicationBasic(String id);

  List<String> getPapers(String id, int start, int end);

  ISearchResult searchByCond(String keyword, int start, int end, int page);
}
