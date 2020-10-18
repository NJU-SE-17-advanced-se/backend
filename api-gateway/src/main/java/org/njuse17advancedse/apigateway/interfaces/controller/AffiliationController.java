package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.AffiliationService;
import org.njuse17advancedse.apigateway.interfaces.dto.IAffiliation;
import org.njuse17advancedse.apigateway.interfaces.dto.IAffiliationBasic;
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

  @ApiOperation("根据机构的id获取机构详细信息")
  @GetMapping("/{id}")
  public IAffiliation getAffiliationById(
    @ApiParam(value = "机构id") @PathVariable String id
  ) {
    return modelMapper.map(
      affiliationService.getAffiliationById(id),
      IAffiliation.class
    );
  }

  @ApiOperation("根据机构的id获取机构简略信息")
  @GetMapping("/{id}/basic-info")
  public IAffiliationBasic getAffiliationBasicInfoById(
    @ApiParam(value = "机构id") @PathVariable String id
  ) {
    return modelMapper.map(
      affiliationService.getAffiliationBasicInfoById(id),
      IAffiliationBasic.class
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
