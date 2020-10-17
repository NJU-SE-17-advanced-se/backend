package org.njuse17advancedse.apigateway.apps.entity;

import org.njuse17advancedse.apigateway.domains.dto.DAffiliation;
import org.njuse17advancedse.apigateway.domains.dto.DAffiliationBasic;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-affiliation")
public interface AffiliationService {
  @GetMapping("/affiliation/{id}")
  DAffiliation getAffiliationById(@PathVariable String id);

  @GetMapping("/affiliation/{id}/basic-info")
  DAffiliationBasic getAffiliationBasicInfoById(@PathVariable String id);
}
