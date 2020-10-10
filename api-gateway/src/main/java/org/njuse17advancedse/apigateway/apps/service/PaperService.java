package org.njuse17advancedse.apigateway.apps.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.domains.po.task.reviewer.PResearcher;
import org.njuse17advancedse.apigateway.domains.repo.entity.PaperRepo;
import org.njuse17advancedse.apigateway.domains.repo.task.CitationAnalysisRepo;
import org.njuse17advancedse.apigateway.domains.repo.task.ImpactAnalysisRepo;
import org.njuse17advancedse.apigateway.domains.repo.task.ReviewerRecommendationRepo;
import org.njuse17advancedse.apigateway.infra.entity.Paper;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IPaper;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.IResearcher;
import org.njuse17advancedse.apigateway.interfaces.dto.paper.req.IPaperUpload;
import org.springframework.stereotype.Service;

@Service
public class PaperService {
  private final CitationAnalysisRepo citationAnalysisRepo;

  private final ImpactAnalysisRepo impactAnalysisRepo;

  private final ModelMapper modelMapper;

  private final PaperRepo paperRepo;

  private final ReviewerRecommendationRepo reviewerRecommendationRepo;

  // 查看某论文引用情况
  public List<IPaper> getReferences(String id) throws Exception {
    List<IPaper> res = new ArrayList<>();
    List<Long> citations = citationAnalysisRepo.getPaperReferences(id);
    for (long paperId : citations) {
      Paper paper = paperRepo.getPaperById(String.valueOf(paperId));
      res.add(modelMapper.map(paper, IPaper.class));
    }
    return res;
  }

  // 查看某论文被引情况
  public List<IPaper> getCitations(String id) throws Exception {
    List<IPaper> res = new ArrayList<>();
    List<Long> citations = citationAnalysisRepo.getPaperCitations(id);
    for (long paperId : citations) {
      Paper paper = paperRepo.getPaperById(String.valueOf(paperId));
      res.add(modelMapper.map(paper, IPaper.class));
    }
    return res;
  }

  // 查看某论文推荐的审稿人
  public List<IResearcher> getRecommendedReviewers(IPaperUpload paper)
    throws Exception {
    List<PResearcher> recommendedReviewers = reviewerRecommendationRepo.getPaperRecommendedReviewers(
      paper
    );
    List<IResearcher> res = new ArrayList<>();
    for (PResearcher reviewer : recommendedReviewers) {
      res.add(modelMapper.map(reviewer, IResearcher.class));
    }
    return res;
  }

  // 查看某论文不推荐的审稿人
  public List<IResearcher> getNotRecommendedReviewers(IPaperUpload paper)
    throws Exception {
    List<PResearcher> notRecommendedReviewers = reviewerRecommendationRepo.getPaperNotRecommendedReviewers(
      paper
    );
    List<IResearcher> res = new ArrayList<>();
    for (PResearcher reviewer : notRecommendedReviewers) {
      res.add(modelMapper.map(reviewer, IResearcher.class));
    }
    return res;
  }

  // 查看某论文的影响力
  public double getImpact(String id) throws Exception {
    return impactAnalysisRepo.getPaperImpact(id);
  }

  public PaperService(
    CitationAnalysisRepo citationAnalysisRepo,
    ImpactAnalysisRepo impactAnalysisRepo,
    ModelMapper modelMapper,
    PaperRepo paperRepo,
    ReviewerRecommendationRepo reviewerRecommendationRepo
  ) {
    this.citationAnalysisRepo = citationAnalysisRepo;
    this.impactAnalysisRepo = impactAnalysisRepo;
    this.modelMapper = modelMapper;
    this.paperRepo = paperRepo;
    this.reviewerRecommendationRepo = reviewerRecommendationRepo;
  }
}
