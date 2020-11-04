package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.DomainService;
import org.njuse17advancedse.apigateway.interfaces.dto.IDomain;
import org.njuse17advancedse.apigateway.interfaces.dto.IDomainBasic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "领域" })
@RequestMapping("/domains")
@RestController
public class DomainController {
  private final DomainService domainService;

  private final ModelMapper modelMapper;

  @ApiOperation("根据 id 获取某领域信息")
  @GetMapping("/{id}")
  public IDomain getDomainById(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    return modelMapper.map(domainService.getDomainById(id), IDomain.class);
  }

  @ApiOperation("根据 id 获取某领域简略信息")
  @GetMapping("/{id}/basic-info")
  public IDomainBasic getDomainBasicInfoById(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    return modelMapper.map(
      domainService.getDomainBasicInfoById(id),
      IDomainBasic.class
    );
  }

  @ApiOperation("根据领域 id 获取某领域下的论文 id")
  @GetMapping("/{id}/papers")
  public List<String> getPapers(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    return domainService.getPapers(id);
  }

  @ApiOperation("根据领域 id 获取某领域下的学者 id")
  @GetMapping("/{id}/researchers")
  public List<String> getResearchers(
    @ApiParam(value = "领域 id") @PathVariable String id
  ) {
    return domainService.getResearchers(id);
  }

  public DomainController(
    DomainService domainService,
    ModelMapper modelMapper
  ) {
    this.domainService = domainService;
    this.modelMapper = modelMapper;
  }
}
