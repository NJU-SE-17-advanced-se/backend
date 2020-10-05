package org.njuse17advancedse.apigateway.apps.service;

import org.njuse17advancedse.apigateway.domains.repo.ImpactAnalysisRepo;
import org.springframework.stereotype.Service;

@Service
public class PaperService {
  private ImpactAnalysisRepo impactAnalysisRepo;

  public double getImpact(String id, String criteria) throws Exception {
    return impactAnalysisRepo.getPaperImpact(id);
  }

  public PaperService(ImpactAnalysisRepo impactAnalysisRepo) {
    this.impactAnalysisRepo = impactAnalysisRepo;
  }
}
