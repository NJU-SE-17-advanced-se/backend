package org.njuse17advancedse.taskpartnershipanalysis.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "task-impact-analysis")
public interface TaskImpactAnalysisService {
  //根据作者id获得作者影响力
  @GetMapping("/impact/researcher/{id}")
  Double getImpactByResearcherId(@PathVariable String id);
}