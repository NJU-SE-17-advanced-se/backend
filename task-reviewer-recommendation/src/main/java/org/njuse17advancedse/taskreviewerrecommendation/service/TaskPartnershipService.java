package org.njuse17advancedse.taskreviewerrecommendation.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "task-partnership-analysis")
public interface TaskPartnershipService {
  //根据作者id获得与作者合作过的作者id列表
  @GetMapping("/partners/{id}")
  List<String> getPartnersByResearcherId(@PathVariable String id);
}
