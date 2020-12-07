package org.njuse17advancedse.entitypaper.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.entitypaper.dto.IPaper;
import org.njuse17advancedse.entitypaper.dto.IPaperBasic;
import org.njuse17advancedse.entitypaper.dto.IResult;
import org.njuse17advancedse.entitypaper.service.PaperService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

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
    int startYear = 0;
    if (start != null) startYear = checkArgument(start);
    int endYear = 0;
    if (end != null) endYear = checkArgument(end);
    if (startYear < 0 || (endYear > 0 && startYear > endYear)) {
      throw Problem.valueOf(
        Status.BAD_REQUEST,
        String.format(
          "Argument '%s' illegal, Date error",
          startYear + "," + endYear
        )
      );
    }
    if (page <= 0) {
      throw Problem.valueOf(
        Status.BAD_REQUEST,
        String.format("Argument '%s' illegal, Page should >= 1", page)
      );
    }
    return service.getPapersByCond(keyword, start, end, page);
  }

  @ApiOperation("根据 id 获取论文详细信息")
  @GetMapping("/{id}")
  public IPaper getPaper(@ApiParam("论文 id") @PathVariable String id) {
    IPaper paper = service.getIPaper(id);
    if (paper.getId() == null) throw Problem.valueOf(
      Status.NOT_FOUND,
      notFound(id)
    );
    return paper;
  }

  @ApiOperation("根据 id 获取论文简略信息")
  @GetMapping("/{id}/basic-info")
  public IPaperBasic getPaperBasicInfo(
    @ApiParam("论文 id") @PathVariable String id
  ) {
    IPaperBasic paperBasic = service.getPaperBasicInfo(id);
    if (paperBasic.getId() == null) throw Problem.valueOf(
      Status.NOT_FOUND,
      notFound(id)
    );
    return paperBasic;
  }

  @ApiOperation("根据 id 获取论文所属领域")
  @GetMapping("/{id}/domains")
  public List<String> getDomains(@ApiParam("论文 id") @PathVariable String id) {
    List<String> res = service.getDomains(id);
    if (res.size() == 1 && res.get(0).equals("Not Found")) {
      throw Problem.valueOf(Status.NOT_FOUND, notFound(id));
    }
    return res;
  }

  @ApiOperation("根据 id 获取论文引用的论文 id")
  @GetMapping("/{id}/citations")
  public List<String> getCitations(
    @ApiParam("论文 id") @PathVariable String id
  ) {
    List<String> res = service.getCitations(id);
    if (res.size() == 1 && res.get(0).equals("Not Found")) {
      throw Problem.valueOf(Status.NOT_FOUND, notFound(id));
    }
    return res;
  }

  public PaperController(PaperService service) {
    this.service = service;
  }

  private int checkArgument(String arg) {
    int value;
    try {
      value = Integer.parseInt(arg);
      return value;
    } catch (NumberFormatException e) {
      throw Problem.valueOf(
        Status.BAD_REQUEST,
        String.format("Argument '%s' illegal, can not parseInt", arg)
      );
    }
  }

  private String notFound(String id) {
    return String.format("Paper '%s' not found", id);
  }
}
