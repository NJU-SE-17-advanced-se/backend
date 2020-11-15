package org.njuse17advancedse.taskimpactanalysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文" })
@RestController
@RequestMapping(value = "/papers")
public class PaperController {
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

  public PaperController(TaskImpactAnalysisService service) {
    this.service = service;
  }
}
