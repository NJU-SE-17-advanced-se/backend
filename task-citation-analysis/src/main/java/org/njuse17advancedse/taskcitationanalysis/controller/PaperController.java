package org.njuse17advancedse.taskcitationanalysis.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/papers")
@RestController
public class PaperController {
  private final TaskCitationAnalysisService service;

  // 某篇论文引用了哪些论文
  // 某篇论文被哪些论文引用
  @ApiOperation(
    value = "查看某论文和其他论文的引用情况",
    notes = "需求 7.1：论文引用其它论文及被其它论文引用情况"
  )
  @GetMapping("/{id}/citations")
  public List<String> getPaperCitations(
    @ApiParam(value = "论文 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      return service.getQuotedPapersByPaperId(id);
    }
    if (type.equals("quoting")) {
      return service.getQuotingPapersByPaperId(id);
    }
    return null;
  }

  // 某篇论文引用了哪些学者
  // 某篇论文被哪些学者引用
  @ApiOperation(
    value = "查看某论文和学者的引用情况",
    notes = "即，论文引用了哪些学者，哪些学者引用了该论文"
  )
  @GetMapping("/{id}/citations/researchers")
  public List<String> getPaperCitedResearchers(
    @ApiParam(value = "论文 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      return service.getPaperQuotedResearcher(id);
    }
    if (type.equals("quoting")) {
      return service.getPaperQuotingResearcher(id);
    }
    return null;
  }

  public PaperController(TaskCitationAnalysisService service) {
    this.service = service;
  }
}
