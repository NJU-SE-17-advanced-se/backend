package org.njuse17advancedse.taskimpactanalysis.controller;

import com.google.common.collect.Lists;
import com.google.inject.internal.cglib.core.$ClassNameReader;
import java.util.ArrayList;
import java.util.List;
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
  @GetMapping(value = "/researchers/{id}")
  public int getHIndex(
    @PathVariable String id,
    @RequestParam(defaultValue = "hIndex", required = false) String type
  ) {
    if (type.equals("hIndex")) return service.getHIndex(id);
    return -1;
  }

  @GetMapping(value = "/researchers")
  public List<Integer> getHIndexList(
    @RequestParam(value = "rids") List<String> rids,
    @RequestParam(defaultValue = "hIndex", required = false) String type
  ) {
    List<Integer> result = new ArrayList<>();
    if (type.equals("hIndex")) {
      for (String id : rids) {
        result.add(service.getHIndex(id));
      }
    }
    return result;
  }

  /**
   * 根据论文ID获取影响力
   */
  @GetMapping(value = "/papers/{id}")
  public double getPaperImpact(@PathVariable String id) {
    return service.getPaperImpact(id);
  }
  //  @GetMapping(value = "")
  //  public int test() {
  //    return 4396;
  //  }
}
