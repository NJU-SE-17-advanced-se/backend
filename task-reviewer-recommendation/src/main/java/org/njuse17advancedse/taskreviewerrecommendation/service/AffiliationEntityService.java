package org.njuse17advancedse.taskreviewerrecommendation.service;

import org.njuse17advancedse.taskreviewerrecommendation.dto.IAffiliation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-affiliation")
public interface AffiliationEntityService {
  //通过id获得机构信息
  @GetMapping("affiliation/{id}")
  IAffiliation getAffiliationById(@PathVariable String id);
}
