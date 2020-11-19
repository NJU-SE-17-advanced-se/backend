package org.njuse17advancedse.entitydomain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.dto.IResult;
import org.njuse17advancedse.entitydomain.exception.BadRequestProblem;
import org.njuse17advancedse.entitydomain.exception.NotFoundProblem;
import org.njuse17advancedse.entitydomain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "领域" })
@RequestMapping("/domains")
@RestController
public class DomainController {
  private final DomainService service;

  @ApiOperation("根据查询条件查询满足条件的领域 id")
  @GetMapping("")
  public IResult getDomainsByCond(
    @ApiParam(value = "查询关键词") @RequestParam String keyword,
    @ApiParam(value = "页数") @RequestParam int page
  ) {
    if (page <= 0) throw new BadRequestProblem(
      Integer.toString(page),
      "Invalid page"
    );
    return service.getDomainsByCond(keyword, page);
  }

  @ApiOperation("根据 id 获取某领域信息")
  @GetMapping("/{id}")
  public IDomain getDomainById(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    IDomain domain = service.getDomainById(id);
    if (domain.getId() == null) throw new NotFoundProblem(id);
    return domain;
  }

  @ApiOperation("根据 id 获取某领域简略信息")
  @GetMapping("/{id}/basic-info")
  public IDomainBasic getDomainBasicInfoById(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    IDomainBasic domainBasic = service.getDomainBasicInfoById(id);
    if (domainBasic.getId() == null) throw new NotFoundProblem(id);
    return domainBasic;
  }

  @ApiOperation("根据领域 id 获取某领域下的论文 id")
  @GetMapping("/{id}/papers")
  public List<String> getPapers(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    return service.getPapers(id);
  }

  @ApiOperation("根据领域 id 获取某领域下的学者 id")
  @GetMapping("/{id}/researchers")
  public List<String> getResearchers(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    return service.getResearchers(id);
  }

  public DomainController(DomainService service) {
    this.service = service;
  }
}
