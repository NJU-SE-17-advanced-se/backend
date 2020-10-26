package org.njuse17advancedse.apigateway.interfaces.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.njuse17advancedse.apigateway.apps.entity.PublicationService;
import org.njuse17advancedse.apigateway.interfaces.dto.IPublication;
import org.njuse17advancedse.apigateway.interfaces.dto.IPublicationBasic;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "出版物" })
@RequestMapping("/publications")
@RestController
public class PublicationController {
  private final ModelMapper modelMapper;

  private final PublicationService publicationService;

  @ApiOperation("根据出版物id获取出版物详细信息")
  @GetMapping("/{id}")
  public IPublication getPublicationById(
    @ApiParam(value = "出版物id") @PathVariable String id
  ) {
    return modelMapper.map(
      publicationService.getPublicationById(id),
      IPublication.class
    );
  }

  @ApiOperation(
    value = "根据其他指标获取出版物id",
    notes = "注意：如果没有任何指标，返回的就是全部的出版物"
  )
  @GetMapping("")
  public List<String> getPublicationsByTimeRange(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return publicationService.getPublicationsByTimeRange(name, start, end);
  }

  @ApiOperation("根据出版物id（和时间范围）获取出版物包含的论文id")
  @GetMapping("/{id}/papers")
  public List<String> getPapersByIdOrTimeRange(
    @ApiParam(value = "出版物id") @PathVariable String id,
    @RequestParam(required = false) String start,
    @RequestParam(required = false) String end
  ) {
    return publicationService.getPapersByIdOrTimeRange(id, start, end);
  }

  @ApiOperation("根据出版物id获取出版物基本信息")
  @GetMapping("/{id}/basic-info")
  public IPublicationBasic getPublicationBasicInfoById(
    @ApiParam(value = "出版物id") @PathVariable String id
  ) {
    return modelMapper.map(
      publicationService.getPublicationBasicInfoById(id),
      IPublicationBasic.class
    );
  }

  public PublicationController(
    ModelMapper modelMapper,
    PublicationService publicationService
  ) {
    this.modelMapper = modelMapper;
    this.publicationService = publicationService;
  }
}
