package org.njuse17advancedse.apigateway.apps.entity;

import java.util.List;
import org.njuse17advancedse.apigateway.domains.dto.DResearcher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "entity-researcher")
public interface ResearcherService {
  @GetMapping("/researchers/{id}")
  // 根据ID获取学者
  DResearcher getResearcherById(@PathVariable String id);

  @GetMapping("/researchers/{id}/papers")
  // 获取某作者的论文
  List<String> getResearcherPapersByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  @GetMapping("/researchers/{id}/domains")
  // 获取作者所属领域
  List<String> getDomainsByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  @GetMapping("/researchers/{id}/affiliations")
  // 获取作者所属机构
  List<String> getAffiliationsByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );
}
