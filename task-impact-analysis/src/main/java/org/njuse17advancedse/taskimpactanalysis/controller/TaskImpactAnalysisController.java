package org.njuse17advancedse.taskimpactanalysis.controller;

import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
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
    if (type.equals("hIndex")) {
      int res = service.getHIndex(id);
      if (res == -1) throw Problem.valueOf(
        Status.NOT_FOUND,
        String.format("Researcher '%s' not found", id)
      );
      if (res == -2) throw Problem.valueOf(
        Status.INTERNAL_SERVER_ERROR,
        "Author data corrupted"
      );
      return res;
    }
    return -1;
  }

  /**
   * 根据论文ID获取影响力
   */
  @GetMapping(value = "/papers/{id}")
  public double getPaperImpact(@PathVariable String id) {
    double res = service.getPaperImpact(id);
    if (res < 0) throw Problem.valueOf(
      Status.NOT_FOUND,
      String.format("Paper '%s' not found", id)
    );
    return res;
  }

  @GetMapping(value = "")
  public String test() {
    return service.test();
  }
}
