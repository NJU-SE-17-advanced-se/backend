package org.njuse17advancedse.apigateway.apps.task;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "task-impact-analysis")
public interface ImpactAnalysisService {
  @GetMapping(value = "/impact/researchers/{id}")
  int getResearcherImpact(@PathVariable String id, @RequestParam String type);

  @GetMapping(value = "/impact/papers/{id}")
  double getPaperImpact(@PathVariable String id, @RequestParam String type);
}
