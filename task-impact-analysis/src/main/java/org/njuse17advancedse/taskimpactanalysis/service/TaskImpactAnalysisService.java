package org.njuse17advancedse.taskimpactanalysis.service;

public interface TaskImpactAnalysisService {
  /**
   * 计算学者影响力（H指数）
   */
  int getHIndex(String researcherId);

  /**
   * 计算论文影响力（被引次数*影响因子）
   */
  double getPaperImpact(String paperId);

  String test();
}
