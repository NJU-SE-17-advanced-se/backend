package org.njuse17advancedse.taskdomainprediction.repository;

import java.util.List;

public interface ResearcherRepository {
  /**
   * 判断学者是否存在
   * @param rid 学者id
   * @return true or false
   */
  boolean containResearcher(String rid);

  /**
   * 获得作者曾经的研究领域
   * @param rid 学者id
   * @return 领域id列表
   */
  List<String> getPastDomains(String rid);

  /**
   * 计算研究领域的影响力
   * @param domain 研究领域id
   * @return double 影响力数值
   */
  Double getDomainImpact(String domain);
}
