package org.njuse17advancedse.entitypublication.repository;

import java.util.List;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;
import org.springframework.cache.annotation.Cacheable;

public interface PublicationRepository {
  @Cacheable("findPublication")
  IPublication findPublication(String id);

  @Cacheable("findPublicationBasic")
  IPublicationBasic findPublicationBasic(String id);

  @Cacheable("getPapers")
  List<String> getPapers(String id, int start, int end);

  // 暂时不打算缓存搜索方法，确保搜索结果是基本准确的
  ISearchResult searchByCond(String keyword, int start, int end, int page);

  @Cacheable("containPublication")
  boolean containPublication(String id);
}
