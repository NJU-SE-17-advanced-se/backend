package org.njuse17advancedse.taskimpactanalysis.controller;

import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/impact")
public class TaskImpactAnalysisController {
  @Autowired
  TaskImpactAnalysisService service;

  /**
   *  根据学者ID获取H指数
   * */
  @GetMapping(value = "/researcher/{id}")
  public int getHIndex(@PathVariable String id, @RequestParam String type) {
    return service.getHIndex(id);
  }

  /**
   * 根据论文ID获取影响力
   */
  @GetMapping(value = "/paper/{id}")
  public double getPaperImpact(
    @PathVariable String id,
    @RequestParam String type
  ) {
    return service.getPaperImpact(id);
  }
  //  @GetMapping(value = "")
  //  public int test() {
  //    return 4396;
  //  }
}
