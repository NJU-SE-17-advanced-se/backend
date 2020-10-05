package org.njuse17advancedse.taskcitationanalysis.controller;

import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/citation")
public class TaskCitationAnalysisController {
  @Autowired
  TaskCitationAnalysisService service;

  /**
   *      type='quoting' 表示获取学者的每篇论文和引用它的论文
   *      type='quoted' 表示获取学者的每篇论文和被它引用的论文
   *
   *
   *
   *
   * */
  @GetMapping(value = "/researcher/{type}/{id}")
  public Map<Long, List<Long>> getResearcher(
    @PathVariable String type,
    @PathVariable Long id
  ) {
    if (type.equals("quoting")) {
      return service.getQuotingPapersByResearcherId(id);
    }
    if (type.equals("quoted")) {
      return service.getQuotedPapersByResearcherId(id);
    }
    return null;
  }

  /**
   *  type='quoting' 表示获取所有引用它的论文
   *  type='quoted' 表示获取被它引用的论文
   * @param type
   * @param id
   * @return
   */
  @GetMapping(value = "/paper/{type}/{id}")
  public List<Long> getPaper(@PathVariable String type, @PathVariable Long id) {
    if (type.equals("quoting")) {
      return service.getQuotingPapersByPaperId(id);
    }
    if (type.equals("quoted")) {
      return service.getQuotedPapersByPaperId(id);
    }
    return null;
  }
}
