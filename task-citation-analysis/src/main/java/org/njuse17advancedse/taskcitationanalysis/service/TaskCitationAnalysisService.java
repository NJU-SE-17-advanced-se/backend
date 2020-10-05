package org.njuse17advancedse.taskcitationanalysis.service;

import java.util.List;
import java.util.Map;

public interface TaskCitationAnalysisService {
  /**
   *  获取学者的论文引用情况
   */
  Map<Long, List<Long>> getQuotingPapersByResearcherId(Long researcherId);

  /**
   * 获取学者论文被引情况
   */

  Map<Long, List<Long>> getQuotedPapersByResearcherId(Long researcherId);

  /**
   * 查看某论文引用情况
   */

  List<Long> getQuotingPapersByPaperId(Long paperId);

  /**
   * 查看某论文被引情况
   */

  List<Long> getQuotedPapersByPaperId(Long paperId);

  //计算一遍数据
  void init();
}
