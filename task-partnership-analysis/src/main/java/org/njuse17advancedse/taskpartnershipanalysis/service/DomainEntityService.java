package org.njuse17advancedse.taskpartnershipanalysis.service;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "entity-domain")
public interface DomainEntityService {
  //根据领域id获得某领域下的学者
  @GetMapping("/domains/{id}/researchers")
  List<String> getResearcherByDomain(@PathVariable String id);
}
