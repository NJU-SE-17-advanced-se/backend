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
  public Map<String, List<String>> getResearcher(
    @PathVariable String type,
    @PathVariable String id
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
  public List<String> getPaper(
    @PathVariable String type,
    @PathVariable String id
  ) {
    if (type.equals("quoting")) {
      return service.getQuotingPapersByPaperId(id);
    }
    if (type.equals("quoted")) {
      return service.getQuotedPapersByPaperId(id);
    }
    return null;
  }

  /**
   *
   * @param researcherId1
   * @param researcherId2
   * @return 第一个学者每篇论文引用的第二个学者的论文数量
   */

  @GetMapping(value = "/researchers")
  public Map<String, Integer> getResearcherQuoteNums(
    @RequestParam String researcherId1,
    @RequestParam String researcherId2
  ) {
    return service.getResearcherQuoteNums(researcherId1, researcherId2);
  }
}
