package org.njuse17advancedse.entitydomain.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.entitydomain.dto.IDomain;
import org.njuse17advancedse.entitydomain.dto.IDomainBasic;
import org.njuse17advancedse.entitydomain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "领域" })
@RequestMapping("/domains")
@RestController
public class DomainController {
  private final DomainService service;

  @ApiOperation("根据 id 获取某领域信息")
  @GetMapping("/{id}")
  public IDomain getDomainById(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    return service.getDomainById(id);
  }

  @ApiOperation("根据 id 获取某领域简略信息")
  @GetMapping("/{id}/basic-info")
  public IDomainBasic getDomainBasicInfoById(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    return service.getDomainBasicInfoById(id);
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
