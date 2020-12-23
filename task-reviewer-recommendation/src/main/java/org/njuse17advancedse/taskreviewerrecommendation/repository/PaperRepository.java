package org.njuse17advancedse.taskreviewerrecommendation.repository;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.lang.Nullable;

public interface PaperRepository {
  /**
   * 获得引用文献的作者
   * @param references 引文id列表
   * @param partners 合作者列表
   * @return 作者id列表
   */
  @Cacheable("getResearcherIdsFromReferences")
  List<String> getResearcherIdsFromReferences(
    List<String> references,
    List<String> partners
  );

  /**
   * 获得同出版社发表论文作者列表
   * @param publication 出版社id
   * @param date 时间（年份）
   * @param partners 合作者列表
   * @return 作者id列表
   */
  @Cacheable("getResearchersFromPublication")
  List<String> getResearchersFromPublication(
    String publication,
    int date,
    List<String> partners
  );

  /**
   * 获得相同领域作者列表
   * @param domains 领域id列表
   * @param date 时间（年份）
   * @param partners 合作者列表
   * @return 作者id列表
   */
  @Cacheable("getResearcherFromSimilarDomain")
  List<String> getResearcherFromSimilarDomain(
    List<String> domains,
    int date,
    @Nullable List<String> partners
  );

  /**
   * 获得曾经的合作者
   * @param researcherIds 作者id列表
   * @return 合作者id列表
   */
  @Cacheable("getPastPartners")
  List<String> getPastPartners(List<String> researcherIds);

  /**
   * 获得同机构作者列表
   * @param researcherIds 作者列表
   * @return 作者id列表
   */
  @Cacheable("getPartnersByAffiliation")
  List<String> getPartnersByAffiliation(List<String> researcherIds);

  /**
   * 判断出版社是否存在
   * @param id 出版社id
   * @return true or false
   */
  @Cacheable("containPublication")
  boolean containPublication(String id);

  /**
   * 获得作者的影响力
   * @param researcherId 作者id
   * @return HIndex
   */
  @Cacheable("getImpactByResearcherId")
  Integer getImpactByResearcherId(String researcherId);
}
