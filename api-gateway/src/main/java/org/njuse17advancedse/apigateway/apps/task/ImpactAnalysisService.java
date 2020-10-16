package org.njuse17advancedse.apigateway.apps.task;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "task-impact-analysis")
@RequestMapping("/impact")
public interface ImpactAnalysisService {
  @GetMapping(value = "/researcher/{id}")
  int getResearcherImpact(@PathVariable String id, @RequestParam String type);

  @GetMapping(value = "/paper/{id}")
  double getPaperImpact(@PathVariable String id, @RequestParam String type);
}
