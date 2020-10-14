package org.njuse17advancedse.taskcitationanalysis.service;

import java.util.List;
import java.util.Map;

public interface TaskCitationAnalysisService {
  /**
   *  获取学者的论文引用情况
   */
  Map<String, List<String>> getQuotingPapersByResearcherId(String researcherId);

  /**
   * 获取学者论文被引情况
   */

  Map<String, List<String>> getQuotedPapersByResearcherId(String researcherId);

  /**
   * 查看某论文引用情况
   */

  List<String> getQuotingPapersByPaperId(String paperId);

  /**
   * 查看某论文被引情况
   */

  List<String> getQuotedPapersByPaperId(String paperId);

  /**
   *
   */
  Map<String, Integer> getResearcherQuoteNums(
    String researcherId1,
    String researcherId2
  );

  Map<String, List<String>> getResearcherQuotedResearcherNums(
    String researcherId
  );

  Map<String, List<String>> getResearcherQuotingResearcherNums(
    String researcherId
  );
  //计算一遍数据
  void init();
}
