package org.njuse17advancedse.taskimpactanalysis.service.impl;

import java.util.*;
import org.njuse17advancedse.taskimpactanalysis.data.AllRepository;
import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskImpactAnalysisServiceImpl
  implements TaskImpactAnalysisService {

  public TaskImpactAnalysisServiceImpl() {
    impactFactors.put("", 1d);
  }

  @Autowired
  AllRepository repository;

  /**
   * 计算学者影响力（H指数）
   */
  public int getHIndex(String id) {
    try {
      if (!repository.existsResearcherById(id)) return -1;
      List<Integer> citations = repository.getPaperQuotingTimes(id);
      citations.sort(Comparator.comparingInt(a -> -a));
      int res = 0;
      for (int i = 0; i < citations.size(); i++) {
        if (citations.get(i) > i) {
          res = i + 1;
        } else {
          break;
        }
      }
      return res;
    } catch (Exception e) {
      if (e.getMessage().contains("Researcher")) return -1;
      return -2;
    }
  }

  static HashMap<String, Double> impactFactors = new HashMap<>();

  /**
   * 计算论文影响力（被引次数）
   */
  @Override
  public double getPaperImpact(String id) {
    try {
      if (!repository.existsPaperById(id)) return -1;
      int size = repository.getSinglePaperQuotingTimes(id);
      return Double.parseDouble(
        String.format(
          "%.2f",
          size * impactFactors.getOrDefault("publication//TODO", 1d)
        )
      );
    } catch (Exception e) {
      return -1;
    }
  }

  public String test() {
    return "p.getTitle();;";
  }
}
