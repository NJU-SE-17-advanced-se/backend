package org.njuse17advancedse.taskimpactanalysis.service.impl;

import java.util.*;
import org.njuse17advancedse.taskimpactanalysis.data.AllRepository;
import org.njuse17advancedse.taskimpactanalysis.dto.ICitation;
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
  AllRepository repository;

  /**
   * 计算学者影响力（H指数）
   */
  public int getHIndex(String id) {
    try {
      if (!repository.existsResearcherById(id)) {
        return -1;
      }
      List<ICitation> citations = repository.getPaperQuotingTimes(id);

      citations.sort(Comparator.comparingInt(a -> -a.getCitation()));
      int res = 0;
      for (int i = 0; i < citations.size(); i++) {
        if (citations.get(i).getCitation() > i) {
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

  static HashMap<String, Double> impactFactors;

  /**
   * 计算论文影响力（被引次数）
   */
  @Override
  public double getPaperImpact(String id) {
    try {
      IPaper p = paperService.getPaper(id);
      if (isEmptyPaper(p)) return -1;
      int size = paperService.getCitations(id).size();
      return Double.parseDouble(
        String.format(
          "%.2f",
          size * impactFactors.getOrDefault(p.getPublication(), 1d)
        )
      );
    } catch (Exception e) {
      return -1;
    }
  }

  public String test() {
    IPaper p = paperService.getPaper("f9e5a809fc0e03c3dd75d87e6b6f05bf");
    //    System.out.println(p.getAbs());
    //    IResearcher r = researcherService.getResearcherById("IEEE_37317862200");
    //    System.out.println(r.getName());
    //    return p.getTitle() + " " + r.getName();
    return p.getTitle();
  }

  private boolean isEmptyResearcher(IResearcher r) {
    return r.getId() == null;
  }

  private boolean isEmptyPaper(IPaper p) {
    return p.getId() == null;
  }
}
