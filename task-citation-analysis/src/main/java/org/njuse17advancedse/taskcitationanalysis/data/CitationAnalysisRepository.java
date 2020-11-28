package org.njuse17advancedse.taskcitationanalysis.data;

import java.util.List;
import java.util.Map;

public interface CitationAnalysisRepository {
  //某学者是否存在
  boolean existsResearcherById(String id);

  //某论文是否存在
  boolean existsPaperById(String id);

  //获取一篇论文引用了哪些论文
  List<String> getQuotingPapersByPaperId(String paperId);

  //获取一篇论文被哪些论文引用
  List<String> getQuotedPapersByPaperId(String paperId);

  //获取一篇论文引用了哪些学者
  List<String> getPaperQuotedResearcher(String paperId);

  //获取一篇论文被哪些学者引用
  List<String> getPaperQuotingResearcher(String paperId);

  //获取一个学者的论文列表
  List<String> getPapersByResearcherId(String researcherId);

  //获取一个学者每篇论文引用的论文列表
  Map<String, List<String>> getResearcherPaperQuotedPapers(String researcherId);

  //获取一个学者每篇论文被引的论文列表
  Map<String, List<String>> getResearcherPaperQuotingPapers(
    String researcherId
  );

  //获取一个学者每篇论文引用的学者列表
  Map<String, List<String>> getResearcherPaperQuotingResearchers(
    String researcherId
  );

  //获取一个学者每篇论文被引的学者列表
  public Map<String, List<String>> getResearcherPaperQuotedResearchers(
    String researcherId
  );

  //某学者引用了哪些学者
  List<String> getResearcherQuotedResearcher(String researcherId);
  //某学者被哪些学者引用
  List<String> getResearcherQuotingResearcher(String researcherId);
}
