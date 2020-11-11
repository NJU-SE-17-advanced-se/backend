package org.njuse17advancedse.taskimpactanalysis.service;

import java.util.List;
import org.njuse17advancedse.taskimpactanalysis.dto.IPaper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "entity-paper")
public interface PaperService {
  @GetMapping("/papers/{id}")
  // 根据ID获取论文
  IPaper getPaper(@PathVariable String id);

  @GetMapping("papers/{id}/citations")
  List<String> getCitations(@PathVariable String id);
}
