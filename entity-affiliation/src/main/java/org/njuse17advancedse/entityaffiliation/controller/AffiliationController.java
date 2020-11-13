package org.njuse17advancedse.entityaffiliation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.service.AffiliationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "机构" })
@RequestMapping("/affiliations")
@RestController
public class AffiliationController {
  private final AffiliationService service;

  @ApiOperation("根据机构 id 获取机构详细信息")
  @GetMapping("/{id}")
  public IAffiliation getAffiliationById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    return service.getAffiliationById(id);
  }

  @ApiOperation("根据机构 id 获取机构简略信息")
  @GetMapping("/{id}/basic-info")
  public IAffiliationBasic getAffiliationBasicInfoById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    return service.getAffiliationBasicInfoById(id);
  }

  @ApiOperation("根据机构 id 获取该机构的学者 id")
  @GetMapping("/{id}/researchers")
  public List<String> getAffiliationResearchersById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    return service.getAffiliationResearchersById(id);
  }

  @ApiOperation("根据机构 id 获取该机构发表的论文 id")
  @GetMapping("/{id}/papers")
  public List<String> getAffiliationPapersById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    return service.getAffiliationPapersById(id);
  }

  @ApiOperation("根据机构 id 获取该机构的研究领域 id")
  @GetMapping("/{id}/domains")
  public List<String> getAffiliationDomainsById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    return service.getAffiliationDomainsById(id);
  }

  public AffiliationController(AffiliationService service) {
    this.service = service;
  }
}
