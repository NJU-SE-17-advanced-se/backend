package org.njuse17advancedse.taskimpactanalysis.service;

import org.njuse17advancedse.taskimpactanalysis.entity.Journal;
import org.njuse17advancedse.taskimpactanalysis.entity.Paper;
import org.njuse17advancedse.taskimpactanalysis.entity.Researcher;

public interface TaskImpactAnalysisService {
  /**
   * 计算学者影响力（H指数）
   */
  int getHIndex(String researcherId);

  /**
   * 计算论文影响力（被引次数*影响因子）
   */
  double getPaperImpact(String paperId);
}
