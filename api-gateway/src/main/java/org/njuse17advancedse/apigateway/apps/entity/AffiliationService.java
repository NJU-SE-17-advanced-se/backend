package org.njuse17advancedse.apigateway.apps.entity;

import java.util.List;
import org.njuse17advancedse.apigateway.domains.dto.DAffiliation;
import org.njuse17advancedse.apigateway.domains.dto.DAffiliationBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-affiliation")
public interface AffiliationService {
  @GetMapping("/affiliations/{id}")
  // 根据机构的id获取机构详细信息
  DAffiliation getAffiliationById(@PathVariable String id);

  @GetMapping("/affiliations/{id}/basic-info")
  // 根据机构的id获取机构简略信息
  DAffiliationBasic getAffiliationBasicInfoById(@PathVariable String id);

  @GetMapping("/affiliations/{id}/researchers")
  // 根据机构的id获取机构学者
  List<String> getAffiliationResearchersById(@PathVariable String id);

  @GetMapping("/affiliations/{id}/papers")
  // 根据机构的id获取机构论文
  List<String> getAffiliationPapersById(@PathVariable String id);

  @GetMapping("/affiliations/{id}/domains")
  // 根据机构的id获取机构研究领域
  List<String> getAffiliationDomainsById(@PathVariable String id);
}
