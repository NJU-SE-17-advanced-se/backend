package org.njuse17advancedse.taskdomainprediction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "学者" })
@RestController
@RequestMapping(value = "/researchers")
public class ResearcherController {

  @ApiOperation(
    value = "预测某学者未来的研究方向",
    notes = "需求 4.3：能够初步预测研究者的研究兴趣"
  )
  @GetMapping("/{id}/domains/future")
  public List<String> getFutureDomains(
    @ApiParam(value = "学者 id") @PathVariable String id
  ) {
    return new ArrayList<>();
  }
}
