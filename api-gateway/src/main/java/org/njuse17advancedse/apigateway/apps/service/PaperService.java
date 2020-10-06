package org.njuse17advancedse.apigateway.apps.service;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.apigateway.domains.repo.entity.PaperRepo;
import org.njuse17advancedse.apigateway.domains.repo.task.CitationAnalysisRepo;
import org.njuse17advancedse.apigateway.domains.repo.task.ImpactAnalysisRepo;
import org.njuse17advancedse.apigateway.infra.entity.Paper;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IResearcher;
import org.springframework.stereotype.Service;

@Service
public class PaperService {
  private CitationAnalysisRepo citationAnalysisRepo;
  private ImpactAnalysisRepo impactAnalysisRepo;
  private PaperRepo paperRepo;

  // 查看某论文引用情况
  public List<IPaper> getReferences(String id) throws Exception {
    List<IPaper> res = new ArrayList<>();
    List<Long> citations = citationAnalysisRepo.getPaperReferences(id);
    for (long paperId : citations) {
      Paper paper = paperRepo.getPaperById(String.valueOf(paperId));
      res.add(
        new IPaper(
          paper.getId(),
          paper.getTitle(),
          paper.getAbs(),
          paper.getLink(),
          paper.getResearchers(),
          paper.getDomains(),
          paper.getReferences()
        )
      );
    }
    return res;
  }

  // 查看某论文被引情况
  public List<IPaper> getCitations(String id) throws Exception {
    List<IPaper> res = new ArrayList<>();
    List<Long> citations = citationAnalysisRepo.getPaperCitations(id);
    for (long paperId : citations) {
      Paper paper = paperRepo.getPaperById(String.valueOf(paperId));
      res.add(
        new IPaper(
          paper.getId(),
          paper.getTitle(),
          paper.getAbs(),
          paper.getLink(),
          paper.getResearchers(),
          paper.getDomains(),
          paper.getReferences()
        )
      );
    }
    return res;
  }

  // TODO:查看某论文推荐的审稿人
  public List<IResearcher> getRecommendedReviewers() {
    return new ArrayList<>();
  }

  // TODO:查看某论文不推荐的审稿人
  public List<IResearcher> getNotRecommendedReviewers() {
    return new ArrayList<>();
  }

  // 查看某论文的影响力
  public double getImpact(String id, String criteria) throws Exception {
    return impactAnalysisRepo.getPaperImpact(id);
  }

  public PaperService(
    CitationAnalysisRepo citationAnalysisRepo,
    ImpactAnalysisRepo impactAnalysisRepo,
    PaperRepo paperRepo
  ) {
    this.citationAnalysisRepo = citationAnalysisRepo;
    this.impactAnalysisRepo = impactAnalysisRepo;
    this.paperRepo = paperRepo;
  }
}
