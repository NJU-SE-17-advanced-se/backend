package org.njuse17advancedse.entitypaper.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.service.PaperService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/papers")
@RestController
public class PaperController {
  private final PaperService service;

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

  @ApiOperation(
    value = "根据其他查询条件获取论文 id",
    notes = "如果没有任何查询条件，返回全部论文 id"
  )
  @GetMapping("")
  // TODO: 分页
  public List<String> getPapers(
    @ApiParam("学者信息") @RequestParam(required = false) String researcher,
    @ApiParam("出版物信息") @RequestParam(required = false) String publication,
    @ApiParam("年份") @RequestParam(required = false) String date
  ) {
    return service.getPapers(researcher, publication, date);
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
}
