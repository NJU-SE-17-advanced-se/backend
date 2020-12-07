package org.njuse17advancedse.taskcitationanalysis.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskcitationanalysis.data.CitationAnalysisRepository;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskCitationAnalysisServiceImpl
  implements TaskCitationAnalysisService {
  @Autowired
  CitationAnalysisRepository repository;

  /**
   *  获取学者论文被引情况
   */
  @Override
  public Map<String, List<String>> getQuotingPapersByResearcherId(
    String researcherId
  ) {
    try {
      if (
        !repository.existsResearcherById(researcherId)
      ) return getProblemMap();
      return repository.getResearcherPaperQuotingPapers(researcherId);
    } catch (Exception e) {
      return getProblemMap();
    }
  }

  /**
   * 获取学者的论文引用情况
   */
  @Override
  public Map<String, List<String>> getQuotedPapersByResearcherId(
    String researcherId
  ) {
    try {
      if (
        !repository.existsResearcherById(researcherId)
      ) return getProblemMap();
      return repository.getResearcherPaperQuotedPapers(researcherId);
    } catch (Exception e) {
      return getProblemMap();
    }
  }

  /**
   * 查看某论文被引情况
   */
  @Override
  public List<String> getQuotingPapersByPaperId(String paperId) {
    try {
      if (!repository.existsPaperById(paperId)) return getProblemList();
      return repository.getQuotingPapersByPaperId(paperId);
    } catch (Exception e) {
      return getProblemList();
    }
  }

  /**
   * 查看某论文引用情况
   */
  @Override
  public List<String> getQuotedPapersByPaperId(String paperId) {
    try {
      if (!repository.existsPaperById(paperId)) return getProblemList();
      return repository.getQuotedPapersByPaperId(paperId);
    } catch (Exception e) {
      return getProblemList();
    }
  }

  @Override
  public Map<String, List<String>> getResearcherPaperQuotedResearcher(
    String researcherId
  ) {
    try {
      if (!repository.existsResearcherById(researcherId)) {
        return getProblemMap();
      }
      return repository.getResearcherPaperQuotedResearchers(researcherId);
    } catch (Exception e) {
      return getProblemMap();
    }
  }

  @Override
  public Map<String, List<String>> getResearcherPaperQuotingResearcher(
    String researcherId
  ) {
    try {
      if (!repository.existsResearcherById(researcherId)) {
        return getProblemMap();
      }
      return repository.getResearcherPaperQuotingResearchers(researcherId);
    } catch (Exception e) {
      return getProblemMap();
    }
  }

  @Override
  public List<String> getResearcherQuotedResearcher(String researcherId) {
    try {
      if (
        !repository.existsResearcherById(researcherId)
      ) return getProblemList();
      return repository.getResearcherQuotedResearcher(researcherId);
    } catch (Exception e) {
      return getProblemList();
    }
  }

  @Override
  public List<String> getResearcherQuotingResearcher(String researcherId) {
    try {
      if (
        !repository.existsResearcherById(researcherId)
      ) return getProblemList();
      return repository.getResearcherQuotingResearcher(researcherId);
    } catch (Exception e) {
      return getProblemList();
    }
  }

  //某论文引用了哪些学者
  @Override
  public List<String> getPaperQuotedResearcher(String paperId) {
    try {
      if (!repository.existsPaperById(paperId)) return getProblemList();
      return repository.getPaperQuotedResearcher(paperId);
    } catch (Exception e) {
      return getProblemList();
    }
  }

  //某论文被哪些学者引用
  public List<String> getPaperQuotingResearcher(String paperId) {
    try {
      if (!repository.existsPaperById(paperId)) return getProblemList();
      return repository.getPaperQuotingResearcher(paperId);
    } catch (Exception e) {
      return getProblemList();
    }
  }

  private Map<String, List<String>> getProblemMap() {
    Map<String, List<String>> res = new HashMap<>();
    List<String> parms = new ArrayList<>();
    parms.add("Unknown");
    res.put("Not Found", parms);
    return res;
  }

  private List<String> getProblemList() {
    List<String> res = new ArrayList<>();
    res.add("Not Found");
    res.add("Unknown");
    res.add("Unknown Id");
    return res;
  }
}
