package org.njuse17advancedse.taskreviewerrecommendation.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "entity-publication")
public interface PublicationEntityService {
  // 根据ID（和时间范围）获取出版物包含的论文
  @GetMapping("/publications/{id}/papers")
  List<String> getPapersByPublication(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );
}
