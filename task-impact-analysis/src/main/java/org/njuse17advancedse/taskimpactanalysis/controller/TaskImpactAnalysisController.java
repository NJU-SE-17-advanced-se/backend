package org.njuse17advancedse.taskimpactanalysis.controller;

import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(value = "/impact")
public class TaskImpactAnalysisController {
  @Autowired
  TaskImpactAnalysisService service;

  /**
   *  根据学者ID获取H指数
   * */
  @GetMapping(value = "/researchers/{id}")
  public int getHIndex(
    @PathVariable String id,
    @RequestParam(defaultValue = "hIndex", required = false) String type
  ) {
    if (type.equals("hIndex")) return service.getHIndex(id);
    return -1;
  }

  /**
   * 根据论文ID获取影响力
   */
  @GetMapping(value = "/papers/{id}")
  public double getPaperImpact(@PathVariable String id) {
    return service.getPaperImpact(id);
  }

  @GetMapping(value = "")
  public String test() {
    return service.test();
  }
}
