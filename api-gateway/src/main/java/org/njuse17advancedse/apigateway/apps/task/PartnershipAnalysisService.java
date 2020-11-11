package org.njuse17advancedse.apigateway.apps.task;

import java.util.Map;
import org.njuse17advancedse.apigateway.interfaces.dto.IResearcherNet;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "task-partnership-analysis")
public interface PartnershipAnalysisService {
  // 合作关系分析
  @GetMapping(value = "/partnership/{id}/partners-net")
  IResearcherNet getPartnership(
    @PathVariable String id,
    @RequestParam String start,
    @RequestParam String end
  );

  // 合作关系分析预测
  @GetMapping(value = "/partnership/{id}/potential-partners")
  Map<String, Double> getPotentialPartners(@PathVariable String id);
}
