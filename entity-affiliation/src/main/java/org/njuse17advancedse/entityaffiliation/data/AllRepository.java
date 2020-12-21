package org.njuse17advancedse.entityaffiliation.data;

import java.util.List;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.dto.IResult;
import org.springframework.cache.annotation.Cacheable;

public interface AllRepository {
  @Cacheable("getAffiliationById")
  IAffiliation getAffiliationById(String id);

  @Cacheable("getAffiliationBasicInfoById")
  IAffiliationBasic getAffiliationBasicInfoById(String id);

  @Cacheable("existsById")
  boolean existsById(String id);

  @Cacheable("getAffiliationResearchersById")
  List<String> getAffiliationResearchersById(String id);

  @Cacheable("getAffiliationPapersById")
  List<String> getAffiliationPapersById(String id);

  @Cacheable("getAffiliationDomainsById")
  List<String> getAffiliationDomainsById(String id);

  // 暂时不打算缓存搜索方法，确保搜索结果是基本准确的
  IResult getAffiliationsByCond(String keyword, int page);
}
