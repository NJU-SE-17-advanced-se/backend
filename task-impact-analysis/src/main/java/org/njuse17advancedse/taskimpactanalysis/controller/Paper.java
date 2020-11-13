package org.njuse17advancedse.taskimpactanalysis.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/papers")
public class Paper {
  TaskImpactAnalysisService service;

  @ApiOperation(
    value = "查看某论文的影响力",
    notes = "需求 7.3：评价研究影响力"
  )
  @GetMapping(value = "/{id}/impact")
  public double getPaperImpact(
    @ApiParam(value = "论文 id") @PathVariable String id
  ) {
    return service.getPaperImpact(id);
  }

  public Paper(TaskImpactAnalysisService service) {
    this.service = service;
  }
}
