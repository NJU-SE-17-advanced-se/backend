package org.njuse17advancedse.taskpartnershipanalysis.repository;

import java.util.HashMap;
import java.util.List;
import org.njuse17advancedse.taskpartnershipanalysis.entity.JpaPaper;
import org.springframework.cache.annotation.Cacheable;

public interface ResearcherRepository {
  /**
   * 获得作者合作列表
   * @param rid 作者id
   * @return 学者id列表
   */
  @Cacheable("getPartnersByRid")
  List<String> getPartnersByRid(String rid);

  /**
   * 根据时间段获得学者论文列表
   * @param rid 学者id
   * @param start 开始时间
   * @param end 结束时间
   * @return 论文id列表
   */
  @Cacheable("getPapersByRid")
  List<String> getPapersByRid(String rid, int start, int end);

  /**
   * 根据论文获得引文id列表
   * @param papers 论文id列表
   * @return 引文id列表
   */
  @Cacheable("getReferencesByPapers")
  List<String> getReferencesByPapers(List<String> papers);

  /**
   * 判断学者是否存在
   * @param rid 学者id
   * @return true or false
   */
  @Cacheable("containThisResearcher")
  boolean containThisResearcher(String rid);

  /**
   * 获得学者合作map
   * @param researcherId 学者id
   * @param papers 论文列表
   * @return 合作map，学者：合作次数
   */
  @Cacheable("getCoAuthorMap")
  HashMap<String, Integer> getCoAuthorMap(
    String researcherId,
    List<String> papers
  );

  /**
   * 获得学者共引列表
   * @param researcherId 学者id
   * @param references 引文列表
   * @return 共引map，学者：共引次数
   */
  @Cacheable("getCitationMap")
  HashMap<String, Integer> getCitationMap(
    String researcherId,
    List<String> references
  );

  /**
   * 获得相似领域学者列表
   * @param rid 学者id
   * @param domains 领域id列表
   * @param partners 合作者
   * @return 学者id列表
   */
  @Cacheable("getResearchersOfSimilarDomain")
  List<String> getResearchersOfSimilarDomain(
    String rid,
    List<String> domains,
    List<String> partners
  );

  /**
   * 获得学者领域
   * @param rid 学者列表
   * @return 领域id列表
   */
  @Cacheable("getDomainsByResearcherId")
  List<String> getDomainsByResearcherId(String rid);

  /**
   * 获得密切合作学者
   * @param researchId 学者id
   * @return 合作者id列表
   */
  @Cacheable("getNearPartnersByRid")
  List<String> getNearPartnersByRid(String researchId);

  /**
   * 获得合作论文
   * @param researchId 学者id
   * @return 论文列表
   */
  @Cacheable("getPaperDateById")
  List<JpaPaper> getPaperDateById(String researchId);

  /**
   * 获得学者影响力
   * @param researcherId 学者id
   * @return 影响力数值HIndex
   */
  @Cacheable("getImpactByResearcherId")
  Integer getImpactByResearcherId(String researcherId);
}
