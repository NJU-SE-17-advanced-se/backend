package org.njuse17advancedse.taskcitationanalysis.controller;

import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
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
  @GetMapping(value = "/researcher/{id}")
  public Map<String, List<String>> getResearcher(
    @RequestParam String type,
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
   */
  @GetMapping(value = "/paper/{id}")
  public List<String> getPaper(
    @RequestParam String type,
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
   * 返回第一个学者每篇论文引用的第二个学者的论文数量
   */

  @GetMapping(value = "/researchers")
  public Map<String, Integer> getResearcherQuoteNums(
    @RequestParam String researcherId1,
    @RequestParam String researcherId2
  ) {
    return service.getResearcherQuoteNums(researcherId1, researcherId2);
  }

  /**
   * 返回一个学者对其他学者的引用次数或被其他学者引用的次数
   */
  @GetMapping(value = "/researcher/all/{id}")
  public Map<String, List<String>> getResearcherQuoteResearcherNums(
    @RequestParam String type,
    @PathVariable String id
  ) {
    if (type.equals("quoting")) {
      return service.getResearcherQuotingResearcherNums(id);
    }
    if (type.equals("quoted")) {
      return service.getResearcherQuotingResearcherNums(id);
    }
    return null;
  }
}
