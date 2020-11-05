package org.njuse17advancedse.taskreviewerrecommendation.service;

import org.njuse17advancedse.taskreviewerrecommendation.dto.IPaperBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-paper")
public interface PaperEntityService {
  //根据论文id获得论文简要信息
  @GetMapping("/papers/{id}/basic-info")
  IPaperBasic getPaperBasicInfo(@PathVariable String id);
}
