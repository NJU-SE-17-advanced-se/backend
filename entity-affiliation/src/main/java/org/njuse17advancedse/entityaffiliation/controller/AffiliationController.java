package org.njuse17advancedse.entityaffiliation.controller;

import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.service.AffiliationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/affiliations")
@RestController
public class AffiliationController {
  @Autowired
  AffiliationService service;

  @GetMapping("/{id}")
  // 根据机构 id 获取机构详细信息
  public IAffiliation getAffiliationById(@PathVariable String id) {
    return service.getAffiliationById(id);
  }

  @GetMapping("/{id}/basic-info")
  // 根据机构 id 获取机构简略信息
  public IAffiliationBasic getAffiliationBasicInfoById(
    @PathVariable String id
  ) {
    return service.getAffiliationBasicInfoById(id);
  }

  @GetMapping("/{id}/researchers")
  // 根据机构 id 获取该机构的学者
  public List<String> getAffiliationResearchersById(@PathVariable String id) {
    return service.getAffiliationResearchersById(id);
  }

  @GetMapping("/{id}/papers")
  // 根据机构 id 获取该机构发表的论文
  public List<String> getAffiliationPapersById(@PathVariable String id) {
    return service.getAffiliationPapersById(id);
  }

  @GetMapping("/{id}/domains")
  // 根据机构 id 获取该机构的研究领域
  public List<String> getAffiliationDomainsById(@PathVariable String id) {
    return service.getAffiliationDomainsById(id);
  }
}
