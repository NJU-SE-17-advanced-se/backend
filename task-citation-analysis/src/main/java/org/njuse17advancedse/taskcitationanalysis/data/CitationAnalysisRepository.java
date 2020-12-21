package org.njuse17advancedse.taskcitationanalysis.data;

import java.util.List;
import java.util.Map;
import org.springframework.cache.annotation.Cacheable;

public interface CitationAnalysisRepository {
  //某学者是否存在
  @Cacheable("existsResearcherById")
  boolean existsResearcherById(String id);

  //某论文是否存在
  @Cacheable("existsPaperById")
  boolean existsPaperById(String id);

  //获取一篇论文引用了哪些论文
  @Cacheable("getQuotingPapersByPaperId")
  List<String> getQuotingPapersByPaperId(String paperId);

  //获取一篇论文被哪些论文引用
  @Cacheable("getQuotedPapersByPaperId")
  List<String> getQuotedPapersByPaperId(String paperId);

  //获取一篇论文引用了哪些学者
  @Cacheable("getPaperQuotedResearcher")
  List<String> getPaperQuotedResearcher(String paperId);

  //获取一篇论文被哪些学者引用
  @Cacheable("getPaperQuotingResearcher")
  List<String> getPaperQuotingResearcher(String paperId);

  //获取一个学者的论文列表
  @Cacheable("getPapersByResearcherId")
  List<String> getPapersByResearcherId(String researcherId);

  //获取一个学者每篇论文引用的论文列表
  @Cacheable("getResearcherPaperQuotedPapers")
  Map<String, List<String>> getResearcherPaperQuotedPapers(String researcherId);

  //获取一个学者每篇论文被引的论文列表
  @Cacheable("getResearcherPaperQuotingPapers")
  Map<String, List<String>> getResearcherPaperQuotingPapers(
    String researcherId
  );

  //获取一个学者每篇论文引用的学者列表
  @Cacheable("getResearcherPaperQuotingResearchers")
  Map<String, List<String>> getResearcherPaperQuotingResearchers(
    String researcherId
  );

  //获取一个学者每篇论文被引的学者列表
  @Cacheable("getResearcherPaperQuotedResearchers")
  public Map<String, List<String>> getResearcherPaperQuotedResearchers(
    String researcherId
  );

  //某学者引用了哪些学者
  @Cacheable("getResearcherQuotedResearcher")
  List<String> getResearcherQuotedResearcher(String researcherId);

  //某学者被哪些学者引用
  @Cacheable("getResearcherQuotingResearcher")
  List<String> getResearcherQuotingResearcher(String researcherId);
}
