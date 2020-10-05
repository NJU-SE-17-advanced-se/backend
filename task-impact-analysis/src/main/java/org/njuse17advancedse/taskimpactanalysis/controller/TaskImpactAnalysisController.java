package org.njuse17advancedse.taskimpactanalysis.controller;

import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/impact")
public class TaskImpactAnalysisController {
  @Autowired
  TaskImpactAnalysisService service;

  @GetMapping(value = "/hindex/{scholarId}")
  public int getHIndex(@PathVariable String scholarId) {
    return service.getHIndex(scholarId);
  }

  @GetMapping(value = "/paper/{paperId}")
  public double getPaperImpact(@PathVariable String paperId) {
    return service.getPaperImpact(paperId);
  }
  //  @GetMapping(value = "")
  //  public int test() {
  //    return 4396;
  //  }
}
