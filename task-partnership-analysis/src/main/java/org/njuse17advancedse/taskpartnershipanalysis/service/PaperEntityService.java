package org.njuse17advancedse.taskpartnershipanalysis.service;

import org.njuse17advancedse.taskpartnershipanalysis.dto.IPaper;
import org.njuse17advancedse.taskpartnershipanalysis.dto.IPaperBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-paper")
public interface PaperEntityService {
  //根据论文id获得论文简要信息
  @GetMapping("/papers/basic-info/{id}")
  IPaperBasic getPaperBasicInfo(@PathVariable String id);

  //根据论文id获得论文信息
  @GetMapping("/papers/{id}")
  IPaper getPaper(@PathVariable String id);
}
