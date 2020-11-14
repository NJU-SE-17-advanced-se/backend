package org.njuse17advancedse.taskimpactanalysis.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/researchers")
public class Researcher {
  TaskImpactAnalysisService service;

  @ApiOperation(
    value = "查看某学者的影响力",
    notes = "需求 7.3：评价研究者影响力"
  )
  @GetMapping(value = "/{id}/impact")
  public int getHIndex(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "影响力标准") @RequestParam(
      defaultValue = "hIndex",
      required = false
    ) String type
  ) {
    if (type.equals("hIndex")) return service.getHIndex(id);
    return -1;
  }

  public Researcher(TaskImpactAnalysisService service) {
    this.service = service;
  }
}
