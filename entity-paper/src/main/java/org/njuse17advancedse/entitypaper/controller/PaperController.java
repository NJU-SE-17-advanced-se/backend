package org.njuse17advancedse.entitypaper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.njuse17advancedse.entitypaper.service.PaperService;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文" })
@RequestMapping("/papers")
@RestController
public class PaperController {
  private final PaperService service;

  @ApiOperation("根据查询条件查询满足条件的论文 id")
  @GetMapping("")
  public IResult getPapersByCond(
    @ApiParam(value = "查询关键词") @RequestParam String keyword,
    @ApiParam("开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam("结束年份，形如'2020'") @RequestParam(
      required = false
    ) String end,
    @ApiParam(value = "页数") @RequestParam int page
  ) {
    return service.getPapersByCond(keyword, start, end, page);
  }

  @ApiOperation("根据 id 获取论文详细信息")
  @GetMapping("/{id}")
  public IPaper getPaper(@ApiParam("论文 id") @PathVariable String id) {
    return service.getIPaper(id);
  }

  @ApiOperation("根据 id 获取论文简略信息")
  @GetMapping("/{id}/basic-info")
  public IPaperBasic getPaperBasicInfo(
    @ApiParam("论文 id") @PathVariable String id
  ) {
    return service.getPaperBasicInfo(id);
  }

  @ApiOperation("根据 id 获取论文所属领域")
  @GetMapping("/{id}/domains")
  public List<String> getDomains(@ApiParam("论文 id") @PathVariable String id) {
    return service.getDomains(id);
  }

  @ApiOperation("根据 id 获取论文引用的论文 id")
  @GetMapping("/{id}/citations")
  public List<String> getCitations(
    @ApiParam("论文 id") @PathVariable String id
  ) {
    return service.getCitations(id);
  }

  public PaperController(PaperService service) {
    this.service = service;
  }

  @GetMapping("/selection")
  // 根据其他指标获取论文
  // 如果都没填，返回全部论文
  List<String> getPapers(
    @RequestParam(required = false) String researcher,
    @RequestParam(required = false) String publication,
    @RequestParam(required = false) String date
  ) {
    return service.getPapers(researcher, publication, date);
  }
}
