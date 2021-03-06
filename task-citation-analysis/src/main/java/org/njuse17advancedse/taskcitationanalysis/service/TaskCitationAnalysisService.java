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

  Map<String, List<String>> getResearcherPaperQuotedResearcher(
    String researcherId
  );

  //某学者每篇论文被那些学者引用
  Map<String, List<String>> getResearcherPaperQuotingResearcher(
    String researcherId
  );

  //某学者引用了哪些学者
  List<String> getResearcherQuotedResearcher(String researcherId);
  //某学者被哪些学者引用
  List<String> getResearcherQuotingResearcher(String researcherId);

  //某论文引用了哪些学者
  List<String> getPaperQuotedResearcher(String paperId);
  //某论文被哪些学者引用
  List<String> getPaperQuotingResearcher(String paperId);
  //计算一遍数据
}
