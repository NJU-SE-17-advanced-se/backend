package org.njuse17advancedse.taskreviewerrecommendation.service;

import java.util.List;
import org.njuse17advancedse.taskreviewerrecommendation.dto.IResearcher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-researcher")
public interface ResearcherEntityService {
  //根据作者id获得作者的研究领域
  @GetMapping("/researchers/{id}/domains")
  List<String> getDomainsByResearcherId(@PathVariable String id);

  //根据作者id获得作者基本信息
  @GetMapping("/researchers/{id}")
  IResearcher getResearcherById(@PathVariable String id);
}
