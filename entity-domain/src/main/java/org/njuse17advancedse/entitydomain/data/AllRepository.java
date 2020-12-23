package org.njuse17advancedse.entitydomain.data;

import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;
import org.springframework.cache.annotation.Cacheable;

public interface AllRepository {
  @Cacheable("getDomain")
  IDomain getDomain(String domainId);

  @Cacheable("getDomainBasic")
  IDomainBasic getDomainBasic(String domainId);

  @Cacheable("getPapers")
  List<String> getPapers(String domainId);

  @Cacheable("getResearchers")
  List<String> getResearchers(String domainId);

  // 暂时不打算缓存搜索方法，确保搜索结果是基本准确的
  IResult getDomainsByCond(String keyword, int page);

  @Cacheable("existsById")
  boolean existsById(String domainId);
}
