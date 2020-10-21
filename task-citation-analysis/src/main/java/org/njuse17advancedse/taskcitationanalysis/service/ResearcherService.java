package org.njuse17advancedse.taskcitationanalysis.service;

import org.njuse17advancedse.taskcitationanalysis.dto.IResearcher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-researcher")
public interface ResearcherService {
  @GetMapping("/researchers/{id}")
  // 根据ID获取学者
  IResearcher getResearcherById(@PathVariable String id);
}
