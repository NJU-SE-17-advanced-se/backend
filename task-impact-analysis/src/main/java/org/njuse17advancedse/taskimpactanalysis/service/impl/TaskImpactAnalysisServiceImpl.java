package org.njuse17advancedse.taskimpactanalysis.service.impl;

import java.text.NumberFormat;
import java.util.*;
import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.njuse17advancedse.taskimpactanalysis.vo.PaperVo;
import org.njuse17advancedse.taskimpactanalysis.vo.ScholarVo;
import org.springframework.stereotype.Service;

@Service
public class TaskImpactAnalysisServiceImpl
  implements TaskImpactAnalysisService {

  public TaskImpactAnalysisServiceImpl() {
    impactFactors = new HashMap<>();
    impactFactors.put("", 1d);
  }

  /**
   * 计算学者影响力（H指数）
   */
  @Override
  public int getHIndex(ScholarVo vo) {
    ArrayList<PaperVo> tmpPapers = new ArrayList<>(vo.getPapers());
    tmpPapers.sort(Comparator.comparingInt(a -> -a.getQuotingIds().size()));
    int res = 0;
    for (int i = 0; i < tmpPapers.size(); i++) {
      if (tmpPapers.get(i).getQuotingIds().size() > i) {
        res = i + 1;
      } else {
        break;
      }
    }
    return res;
  }

  static HashMap<String, Double> impactFactors;

  /**
   * 计算论文影响力（被引次数）
   */
  @Override
  public double getPaperImpact(PaperVo vo) {
    return Double.parseDouble(
      String.format(
        "%.2f",
        vo.getQuotingIds().size() *
        impactFactors.getOrDefault(vo.getPublisher(), 0d)
      )
    );
  }
}
