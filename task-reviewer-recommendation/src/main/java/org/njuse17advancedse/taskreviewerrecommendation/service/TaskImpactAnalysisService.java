package org.njuse17advancedse.taskreviewerrecommendation.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "task-impact-analysis")
public interface TaskImpactAnalysisService {
  //根据作者id获得作者影响力
  @GetMapping("/impact/researchers/{id}")
  Integer getImpactByResearcherId(@PathVariable String id);
}
