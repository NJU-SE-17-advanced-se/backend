package org.njuse17advancedse.taskdomainprediction.repository;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface ResearcherRepository {
  /**
   * 判断学者是否存在
   * @param rid 学者id
   * @return true or false
   */
  @Cacheable("containResearcher")
  boolean containResearcher(String rid);

  /**
   * 获得作者曾经的研究领域
   * @param rid 学者id
   * @return 领域id列表
   */
  @Cacheable("getPastDomains")
  List<String> getPastDomains(String rid);

  /**
   * 计算研究领域的影响力
   * @param domain 研究领域id
   * @return double 影响力数值
   */
  @Cacheable("getDomainImpact")
  Double getDomainImpact(String domain);

  /**
   * 获得合作学者最近的研究领域列表
   * @param rid 学者id
   * @param pastDomains
   * @return 领域id列表
   */
  @Cacheable("getPartnerDomains")
  List<String> getPartnerDomains(String rid, List<String> pastDomains);
}
