package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.DomainService;
import org.njuse17advancedse.apigateway.interfaces.dto.IDomain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "研究方向" })
@RequestMapping("/domain")
@RestController
public class DomainController {
  private final DomainService domainService;

  private final ModelMapper modelMapper;

  @ApiOperation("根据研究方向的id获取研究方向详细信息（WIP)")
  @Deprecated
  @GetMapping("/{id}")
  // TODO: 完成该接口
  public IDomain getDomainById(
    @ApiParam(value = "论文id") @PathVariable String id
  ) {
    return modelMapper.map(domainService.getDomainById(id), IDomain.class);
  }

  public DomainController(
    DomainService domainService,
    ModelMapper modelMapper
  ) {
    this.domainService = domainService;
    this.modelMapper = modelMapper;
  }
}
