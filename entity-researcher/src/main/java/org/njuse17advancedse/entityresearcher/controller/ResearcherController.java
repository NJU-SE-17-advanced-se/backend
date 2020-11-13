package org.njuse17advancedse.entityresearcher.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.njuse17advancedse.entityresearcher.dto.IResearcher;
import org.njuse17advancedse.entityresearcher.dto.IResearcherBasic;
import org.njuse17advancedse.entityresearcher.service.ResearcherEntityService;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "学者" })
@RequestMapping("/researchers")
@RestController
public class ResearcherController {
  private final ResearcherEntityService researcherEntityService;

  @ApiOperation("根据查询条件查询满足条件的学者 id")
  @GetMapping("")
  public List<String> getResearchersByCond(
    @ApiParam(value = "查询关键词") @RequestParam String keyword,
    @ApiParam(value = "页数") @RequestParam int page
  ) {
    return new ArrayList<>();
  }

  @ApiOperation("根据 id 获取学者详细信息")
  @GetMapping("/{id}")
  public IResearcher getResearcherById(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    return researcherEntityService.getResearcherById(id);
  }

  @ApiOperation("根据学者 id 获取学者简略信息")
  @GetMapping("/{id}/basic-info")
  public IResearcherBasic getResearcherBasicInfoById(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
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
    return researcherEntityService.getPapersByRid(id, start, end);
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
    return researcherEntityService.getDomainByRid(id, start, end);
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
    return researcherEntityService.getAffiliationByRid(id, start, end);
  }

  public ResearcherController(ResearcherEntityService researcherEntityService) {
    this.researcherEntityService = researcherEntityService;
  }
}
