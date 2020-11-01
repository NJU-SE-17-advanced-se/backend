package org.njuse17advancedse.apigateway.apps.entity;

import java.util.List;
import org.njuse17advancedse.apigateway.domains.dto.DResearcher;
import org.njuse17advancedse.apigateway.domains.dto.DResearcherBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "entity-researcher")
public interface ResearcherService {
  @GetMapping("/researchers/{id}")
  // 根据 id 获取学者详细信息
  DResearcher getResearcherById(@PathVariable String id);

  @GetMapping("/researchers/{id}/papers")
  // 获取某作者的论文 id
  List<String> getResearcherPapersByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  @GetMapping("/researchers/{id}/domains")
  // 获取作者所属领域 id
  List<String> getDomainsByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  @GetMapping("/researchers/{id}/affiliations")
  // 获取作者所属机构 id
  List<String> getAffiliationsByTimeRange(
    @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  );

  @GetMapping("/{id}/basic-info")
  // 根据学者 id 获取学者简略信息
  DResearcherBasic getResearcherBasicInfoById(@PathVariable String id);
}
