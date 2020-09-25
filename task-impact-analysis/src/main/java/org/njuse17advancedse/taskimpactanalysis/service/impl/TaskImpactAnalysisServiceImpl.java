package org.njuse17advancedse.taskimpactanalysis.service.impl;

import java.rmi.server.RemoteServer;
import java.util.*;
import org.njuse17advancedse.taskimpactanalysis.entity.Paper;
import org.njuse17advancedse.taskimpactanalysis.entity.Researcher;
import org.njuse17advancedse.taskimpactanalysis.service.FakeService;
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
  FakeService service;

  /**
   * 计算学者影响力（H指数）
   */
  @Override
  public int getHIndex(String id) {
    Researcher r = service.getResearcherById(id);
    ArrayList<Paper> tmpPapers = new ArrayList<>(r.getPapers());
    tmpPapers.sort(Comparator.comparingInt(a -> -a.getQuotedIds().size()));
    int res = 0;
    for (int i = 0; i < tmpPapers.size(); i++) {
      if (tmpPapers.get(i).getQuotedIds().size() > i) {
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
    Paper p = service.getPaperById(id);
    List<String> sss = p.getQuotedIds();
    return Double.parseDouble(
      String.format(
        "%.2f",
        p.getQuotedIds().size() * impactFactors.getOrDefault(p.getJournal(), 0d)
      )
    );
  }
}
