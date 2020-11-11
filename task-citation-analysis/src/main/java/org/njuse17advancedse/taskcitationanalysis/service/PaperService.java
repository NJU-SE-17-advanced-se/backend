package org.njuse17advancedse.taskcitationanalysis.service;

import java.util.List;
import org.njuse17advancedse.taskcitationanalysis.dto.IPaper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "entity-paper")
public interface PaperService {
  @GetMapping("/papers/{id}")
  // 根据ID获取论文
  IPaper getPaper(@PathVariable String id);

  @GetMapping("/papers")
  // 根据其他指标获取论文
  // 如果都没填，返回全部论文
  List<String> getPapers(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  );

  @GetMapping("papers/{id}/citations")
  List<String> getCitations(@PathVariable String id);
}
