package org.njuse17advancedse.entitypublication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.dto.ISearchResult;
import org.njuse17advancedse.entitypublication.service.PublicationEntityService;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "出版物" })
@RequestMapping("/publications")
@RestController
public class PublicationController {
  private final PublicationEntityService publicationEntityService;

  @ApiOperation("根据查询条件查询满足条件的出版物 id")
  @GetMapping("")
  public ISearchResult getPublicationsByCond(
    @ApiParam(value = "查询关键词") @RequestParam String keyword,
    @ApiParam("开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam("结束年份，形如'2020'") @RequestParam(
      required = false
    ) String end,
    @ApiParam(value = "页数") @RequestParam int page
  ) {
    return publicationEntityService.searchByCond(keyword, start, end, page);
  }

  @ApiOperation("根据 id 获取出版物详细信息")
  @GetMapping("/{id}")
  public IPublication getPublicationById(
    @ApiParam(value = "出版物 id") @PathVariable String id
  ) {
    return publicationEntityService.getPublicationById(id);
  }

  @ApiOperation("根据 id 获取出版物简略信息")
  @GetMapping("/{id}/basic-info")
  public IPublicationBasic getPublicationBasicInfoById(
    @ApiParam(value = "出版物 id") @PathVariable String id
  ) {
    return publicationEntityService.getIPublicationBasic(id);
  }

  @ApiOperation("根据 id （和时间范围）获取出版物包含的论文 id")
  @GetMapping("/{id}/papers")
  public List<String> getPapersByIdOrTimeRange(
    @ApiParam(value = "出版物 id") @PathVariable String id,
    @ApiParam("开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam("结束年份，形如'2020'") @RequestParam(required = false) String end
  ) {
    return publicationEntityService.getPapersByIdOrTimeRange(id, start, end);
  }

  public PublicationController(
    PublicationEntityService publicationEntityService
  ) {
    this.publicationEntityService = publicationEntityService;
  }
}
