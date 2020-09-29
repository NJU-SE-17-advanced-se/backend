package org.njuse17advancedse.apigateway.apps.service;

import org.njuse17advancedse.apigateway.domains.repo.ImpactAnalysisRepo;
import org.springframework.stereotype.Service;

@Service
public class ResearcherService {
  private ImpactAnalysisRepo impactAnalysisRepo;

  public double getImpact(String id, String criteria) throws Exception {
    return impactAnalysisRepo.getResearcherImpact(id, criteria);
  }

  public ResearcherService(ImpactAnalysisRepo impactAnalysisRepo) {
    this.impactAnalysisRepo = impactAnalysisRepo;
  }
}
