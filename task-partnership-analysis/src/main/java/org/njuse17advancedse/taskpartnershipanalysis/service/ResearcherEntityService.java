package org.njuse17advancedse.taskpartnershipanalysis.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "entity-researcher")
public interface ResearcherEntityService {
  //根据作者id和时间段获得论文
  @GetMapping("/researchers/{id}/papers")
  List<String> getPaperByResearcherId(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  //根据作者id获得作者的领域
  @GetMapping("/researchers/{id}/domains")
  List<String> getDomainsByResearcherId(@PathVariable String id);
}
