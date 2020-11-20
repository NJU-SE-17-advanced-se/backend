package org.njuse17advancedse.taskimpactanalysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Api(tags = { "学者" })
@RestController
@RequestMapping(value = "/researchers")
public class ResearcherController {
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
    if (type.equals("hIndex")) {
      int res = service.getHIndex(id);
      if (res == -1) throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
      if (res == -2) throw Problem.valueOf(
        Status.INTERNAL_SERVER_ERROR,
        String.format("Author data corrupted", id)
      );
      return res;
    }
    return -1;
  }

  public ResearcherController(TaskImpactAnalysisService service) {
    this.service = service;
  }
}
