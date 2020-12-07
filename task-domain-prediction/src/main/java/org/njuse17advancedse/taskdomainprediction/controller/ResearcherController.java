package org.njuse17advancedse.taskdomainprediction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.taskdomainprediction.service.TaskDomainPredictionService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Api(tags = { "学者" })
@RestController
@RequestMapping(value = "/researchers")
public class ResearcherController {
  private final TaskDomainPredictionService taskDomainPredictionService;

  public ResearcherController(
    TaskDomainPredictionService taskDomainPredictionService
  ) {
    this.taskDomainPredictionService = taskDomainPredictionService;
  }

  @ApiOperation(
    value = "预测某学者未来的研究方向",
    notes = "需求 4.3：能够初步预测研究者的研究兴趣"
  )
  @GetMapping("/{id}/domains/future")
  public List<String> getFutureDomains(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    if (!taskDomainPredictionService.containResearcher(id)) {
      throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' is not found", id)
      );
    }
    return taskDomainPredictionService.getFutureDomains(id);
  }
}
