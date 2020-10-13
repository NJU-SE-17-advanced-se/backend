package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.AffiliationService;
import org.njuse17advancedse.apigateway.interfaces.dto.IAffiliation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = { "机构" })
@RequestMapping("/affiliation")
@RestController
public class AffiliationController {
  private final AffiliationService affiliationService;

  private final ModelMapper modelMapper;

  @ApiOperation("根据机构的id获取机构详细信息（WIP)")
  @Deprecated
  @GetMapping("/{id}")
  // TODO: 完成该接口
  public IAffiliation getAffiliationById(
    @ApiParam(value = "论文id") @PathVariable String id
  ) {
    return modelMapper.map(
      affiliationService.getAffiliationById(id),
      IAffiliation.class
    );
  }

  public AffiliationController(
    AffiliationService affiliationService,
    ModelMapper modelMapper
  ) {
    this.affiliationService = affiliationService;
    this.modelMapper = modelMapper;
  }
}
