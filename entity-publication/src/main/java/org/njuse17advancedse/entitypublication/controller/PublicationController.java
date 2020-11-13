package org.njuse17advancedse.entitypublication.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.entitypublication.dto.IPublication;
import org.njuse17advancedse.entitypublication.dto.IPublicationBasic;
import org.njuse17advancedse.entitypublication.service.PublicationEntityService;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "出版物" })
@RequestMapping("/publications")
@RestController
public class PublicationController {
  private final PublicationEntityService publicationEntityService;

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

  @ApiOperation(
    value = "根据其他查询条件获取出版物 id",
    notes = "注意: 如果没有任何查询条件，返回的就是全部出版物 id"
  )
  @GetMapping("")
  public List<String> getPublicationsByTimeRange(
    @ApiParam("出版物名称") @RequestParam(required = false) String name,
    @ApiParam("开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam("结束年份，形如'2020'") @RequestParam(required = false) String end
  ) {
    return publicationEntityService.getPublications(name, start, end);
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
