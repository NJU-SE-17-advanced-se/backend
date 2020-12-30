package org.njuse17advancedse.entityresearcher.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.dto.ISearchResult;
import org.njuse17advancedse.entityresearcher.service.ResearcherEntityService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Api(tags = { "学者" })
@RequestMapping("/researchers")
@RestController
@EnableAsync
public class ResearcherController {
  private final ResearcherEntityService researcherEntityService;

  @ApiOperation("根据查询条件查询满足条件的学者 id")
  @GetMapping("")
  public ISearchResult getResearchersByCond(
    @ApiParam(value = "查询关键词") @RequestParam String keyword,
    @ApiParam(value = "页数") @RequestParam int page
  ) {
    if (page <= 0) {
      throw Problem.valueOf(
        Status.BAD_REQUEST,
        String.format("Argument '%s' illegal, Page should >= 1", page)
      );
    }
    return researcherEntityService.searchByCond(keyword, page);
  }

  @ApiOperation("根据 id 获取学者详细信息")
  @GetMapping("/{id}")
  public IResearcher getResearcherById(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    if (!researcherEntityService.containResearcher(id)) {
      throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
    }
    IResearcher researcher;
    CompletableFuture<IResearcher> completableFuture = researcherEntityService.getResearcherById(
      id
    );
    try {
      researcher = completableFuture.get();
    } catch (InterruptedException | ExecutionException e) {
      Thread.currentThread().interrupt();
      throw Problem.valueOf(Status.INTERNAL_SERVER_ERROR, "内部出错");
    }
    return researcher;
  }

  @ApiOperation("根据学者 id 获取学者简略信息")
  @GetMapping("/{id}/basic-info")
  public IResearcherBasic getResearcherBasicInfoById(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    if (!researcherEntityService.containResearcher(id)) {
      throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
    }
    return researcherEntityService.getResearcherBasicById(id);
  }

  @ApiOperation("获取某作者的论文 id")
  @GetMapping("/{id}/papers")
  public List<String> getResearcherPapersByTimeRange(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam(value = "结束年份，形如'2020'") @RequestParam(
      required = false
    ) String end
  ) {
    int startDate = 0;
    int endDate = Integer.MAX_VALUE;
    checkDate(startDate, endDate, start, end);
    List<String> papers = researcherEntityService.getPapersByRid(
      id,
      startDate,
      endDate
    );
    if (papers.size() == 1) {
      if (papers.get(0).equals("no such researcher")) {
        throw Problem.valueOf(
          Status.NOT_FOUND,
          String.format("Researcher '%s' not found", id)
        );
      }
    }
    return papers;
  }

  @ApiOperation(
    value = "查看某学者某一时间段的研究方向",
    notes = "需求 4.1：能够识别研究者的兴趣\n\n" +
    "需求 4.2：能够识别研究者在不同阶段的研究兴趣"
  )
  @GetMapping("/{id}/domains")
  public List<String> getDomainsByTimeRange(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "开始年份，形如'2020'") String start,
    @ApiParam(value = "结束年份，形如'2020'") String end
  ) {
    if (!researcherEntityService.containResearcher(id)) {
      throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
    }
    int startDate = 0;
    int endDate = Integer.MAX_VALUE;
    checkDate(startDate, endDate, start, end);
    return researcherEntityService.getDomainByRid(id, startDate, endDate);
  }

  @ApiOperation(
    value = "查看某学者某一时间段所在机构",
    notes = "需求 3.1：能够识别同一研究者在不同时间的单位情况"
  )
  @GetMapping("/{id}/affiliations")
  public List<String> getAffiliationsByTimeRange(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "开始年份，形如'2020'") @RequestParam(
      required = false
    ) String start,
    @ApiParam(value = "结束年份，形如'2020'") @RequestParam(
      required = false
    ) String end
  ) {
    if (!researcherEntityService.containResearcher(id)) {
      throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
    }
    int startDate = 0;
    int endDate = Integer.MAX_VALUE;
    checkDate(startDate, endDate, start, end);
    return researcherEntityService.getAffiliationByRid(id, startDate, endDate);
  }

  /**
   * 判断时间参数
   * @param startDate 开始日期
   * @param endDate 结束日期
   * @param start 开始参数
   * @param end 结束参数
   */
  private void checkDate(int startDate, int endDate, String start, String end) {
    if (start != null) {
      startDate = checkArgument(start);
    }
    if (end != null) {
      endDate = checkArgument(end);
    }
    if (startDate < 0 || startDate > endDate) {
      throw Problem.valueOf(
        Status.BAD_REQUEST,
        String.format(
          "Argument '%s' illegal, Date error",
          startDate + "," + endDate
        )
      );
    }
  }

  /**
   * 检查参数是否合法
   * @param arg 参数
   * @return 参数值
   */
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

  public ResearcherController(ResearcherEntityService researcherEntityService) {
    this.researcherEntityService = researcherEntityService;
  }
}
