package org.njuse17advancedse.taskpartnershipanalysis.service;

import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IResearcherNet;

/**
 * @author ycj
 * @date 2020/10/13
 */
public interface TaskPartnershipAnalysisService {
  /**
   * 根据作者id获得与作者合作过的作者
   * @param researcherId 作者id
   * @return id列表
   */
  List<String> getPartners(String researcherId);

  /**
   * 根据作者id获得作者某一时期的合作网络
   * @param researcherId 作者id
   * @param startDate 起日期
   * @param endDate 止日期
   * @return 学者合作网络
   */
  IResearcherNet getPartnership(
    String researcherId,
    int startDate,
    int endDate
  );

  /**
   * 根据作者id获得未来可能合作作者列表
   * @param researchId 作者id
   * @return 预测合作学者列表
   */
  Map<String, Double> getPotentialPartners(String researchId);

  /**
   * 判断学者是否存在
   * @param researcherId 学者id
   * @return true or false
   */
  boolean containResearcher(String researcherId);
}
