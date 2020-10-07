package org.njuse17advancedse.apigateway.apps.service;

import org.njuse17advancedse.apigateway.domains.repo.task.ImpactAnalysisRepo;
import org.springframework.stereotype.Service;

@Service
public class ResearcherService {
  private ImpactAnalysisRepo impactAnalysisRepo;

  public double getImpact(String id) throws Exception {
    return impactAnalysisRepo.getResearcherImpact(id);
  }

  public ResearcherService(ImpactAnalysisRepo impactAnalysisRepo) {
    this.impactAnalysisRepo = impactAnalysisRepo;
  }
}
