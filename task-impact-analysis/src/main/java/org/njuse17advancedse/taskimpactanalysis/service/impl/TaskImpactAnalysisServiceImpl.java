package org.njuse17advancedse.taskimpactanalysis.service.impl;

import java.awt.print.Paper;
import java.util.*;
import org.checkerframework.checker.units.qual.A;
import org.njuse17advancedse.taskimpactanalysis.dto.IPaper;
import org.njuse17advancedse.taskimpactanalysis.dto.IResearcher;
import org.njuse17advancedse.taskimpactanalysis.service.PaperService;
import org.njuse17advancedse.taskimpactanalysis.service.ResearcherService;
import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskImpactAnalysisServiceImpl
  implements TaskImpactAnalysisService {

  public TaskImpactAnalysisServiceImpl() {
    impactFactors = new HashMap<>();
    impactFactors.put("", 1d);
  }

  @Autowired
  PaperService paperService;

  @Autowired
  ResearcherService researcherService;

  /**
   * 计算学者影响力（H指数）
   */
  @Override
  public int getHIndex(String id) {
    IResearcher r = researcherService.getResearcherById(id);
    ArrayList<String> tmpPaperIds = new ArrayList<>(r.getPapers());
    ArrayList<IPaper> tmpPapers = new ArrayList<>();
    for (String s : tmpPaperIds) {
      tmpPapers.add(paperService.getPaper(s));
    }
    tmpPapers.sort(Comparator.comparingInt(a -> -a.getReferences().size()));
    int res = 0;
    for (int i = 0; i < tmpPapers.size(); i++) {
      if (tmpPapers.get(i).getReferences().size() > i) {
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
  public double getPaperImpact(String id) {
    IPaper p = paperService.getPaper(id);
    return Double.parseDouble(
      String.format(
        "%.2f",
        p.getReferences().size() *
        impactFactors.getOrDefault(p.getPublication(), 0d)
      )
    );
  }
}
