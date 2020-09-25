package org.njuse17advancedse.taskimpactanalysis.controller;

import org.njuse17advancedse.taskimpactanalysis.service.TaskImpactAnalysisService;
import org.njuse17advancedse.taskimpactanalysis.vo.PaperVo;
import org.njuse17advancedse.taskimpactanalysis.vo.ScholarVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/impact")
public class TaskImpactAnalysisController {
  @Autowired
  TaskImpactAnalysisService service;

  @GetMapping(value = "/hindex/{scholarId}")
  public int getHIndex(
    @PathVariable Long scholarId,
    @RequestBody ScholarVo vo
  ) {
    return service.getHIndex(vo);
  }

  @GetMapping(value = "/paper/{paperId}")
  public double getPaperImpact(
    @PathVariable Long paperId,
    @RequestBody PaperVo vo
  ) {
    return service.getPaperImpact(vo);
  }

  @GetMapping(value = "")
  public int test() {
    return 4396;
  }
}
