package org.njuse17advancedse.entityaffiliation.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliation;
import org.njuse17advancedse.entityaffiliation.dto.IAffiliationBasic;
import org.njuse17advancedse.entityaffiliation.dto.IResult;
import org.njuse17advancedse.entityaffiliation.service.AffiliationService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Api(tags = { "机构" })
@RequestMapping("/affiliations")
@RestController
public class AffiliationController {
  private final AffiliationService service;

  private static String notFound = "Not Found";

  @ApiOperation("根据查询条件查询满足条件的机构 id")
  @GetMapping("")
  public IResult getAffiliationsByCond(
    @ApiParam(value = "查询关键词") @RequestParam String keyword,
    @ApiParam(value = "页数") @RequestParam int page
  ) {
    if (page <= 0) throw Problem.valueOf(
      Status.BAD_REQUEST,
      String.format("Argument '%s' illegal, Page should >= 1", page)
    );
    return service.getAffiliationsByCond(keyword, page);
  }

  @ApiOperation("根据机构 id 获取机构详细信息")
  @GetMapping("/{id}")
  public IAffiliation getAffiliationById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    IAffiliation affiliation = service.getAffiliationById(id);
    if (affiliation.getId() == null) throw Problem.valueOf(
      Status.NOT_FOUND,
      notFound(id)
    );
    return affiliation;
  }

  @ApiOperation("根据机构 id 获取机构简略信息")
  @GetMapping("/{id}/basic-info")
  public IAffiliationBasic getAffiliationBasicInfoById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    IAffiliationBasic affiliationBasic = service.getAffiliationBasicInfoById(
      id
    );
    if (affiliationBasic.getId() == null) throw Problem.valueOf(
      Status.NOT_FOUND,
      notFound(id)
    );
    return affiliationBasic;
  }

  @ApiOperation("根据机构 id 获取该机构的学者 id")
  @GetMapping("/{id}/researchers")
  public List<String> getAffiliationResearchersById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    List<String> res = service.getAffiliationResearchersById(id);
    if (res.size() == 1 && res.get(0).equals(notFound)) throw Problem.valueOf(
      Status.NOT_FOUND,
      notFound(id)
    );
    return res;
  }

  @ApiOperation("根据机构 id 获取该机构发表的论文 id")
  @GetMapping("/{id}/papers")
  public List<String> getAffiliationPapersById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    List<String> res = service.getAffiliationPapersById(id);
    if (res.size() == 1 && res.get(0).equals(notFound)) throw Problem.valueOf(
      Status.NOT_FOUND,
      notFound(id)
    );
    return res;
  }

  @ApiOperation("根据机构 id 获取该机构的研究领域 id")
  @GetMapping("/{id}/domains")
  public List<String> getAffiliationDomainsById(
    @ApiParam(value = "机构 id") @PathVariable String id
  ) {
    List<String> res = service.getAffiliationDomainsById(id);
    if (res.size() == 1 && res.get(0).equals(notFound)) throw Problem.valueOf(
      Status.NOT_FOUND,
      notFound(id)
    );
    return res;
  }

  public AffiliationController(AffiliationService service) {
    this.service = service;
  }

  private String notFound(String id) {
    return String.format("Affiliation '%s' not found", id);
  }
}
