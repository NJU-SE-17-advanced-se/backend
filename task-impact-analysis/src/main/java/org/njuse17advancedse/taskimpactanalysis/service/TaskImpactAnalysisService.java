package org.njuse17advancedse.taskimpactanalysis.service;

import org.njuse17advancedse.taskimpactanalysis.vo.PaperVo;
import org.njuse17advancedse.taskimpactanalysis.vo.ScholarVo;

public interface TaskImpactAnalysisService {
  /**
   * 计算学者影响力（H指数）
   */
  int getHIndex(ScholarVo vo);

  /**
   * 计算论文影响力（被引次数*影响因子）
   */
  double getPaperImpact(PaperVo vo);
}
