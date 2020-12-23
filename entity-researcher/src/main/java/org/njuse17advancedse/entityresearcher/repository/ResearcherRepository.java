package org.njuse17advancedse.entityresearcher.repository;

import java.util.List;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.dto.ISearchResult;
import org.springframework.cache.annotation.Cacheable;

public interface ResearcherRepository {
  @Cacheable("getResearcherById")
  IResearcher getResearcherById(String id);

  @Cacheable("getResearcherBasic")
  IResearcherBasic getResearcherBasic(String id);

  @Cacheable("findPapers")
  List<String> findPapers(String id, int start, int end);

  @Cacheable("findDomains")
  List<String> findDomains(String id, int start, int end);

  @Cacheable("findAffiliations")
  List<String> findAffiliations(String rid, int start, int end);

  // 暂时不打算缓存搜索方法，确保搜索结果是基本准确的
  ISearchResult searchByCond(String keyword, int page);

  @Cacheable("containResearcher")
  boolean containResearcher(String id);
}
