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
   * type= "hindex" -> 计算H指数 type="paper" ->计算论文影响力
   *
   *
   *
   * */
  @GetMapping(value = "/{type}/{id}")
  public double getHIndex(@PathVariable String type, @PathVariable String id) {
    if (type.equals("hindex")) return (double) service.getHIndex(id);
    if (type.equals("paper")) return service.getPaperImpact(id); else return -1;
  }
  //  @GetMapping(value = "")
  //  public int test() {
  //    return 4396;
  //  }
}
