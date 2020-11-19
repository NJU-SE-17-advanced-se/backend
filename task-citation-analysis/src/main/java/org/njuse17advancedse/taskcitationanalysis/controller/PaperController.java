package org.njuse17advancedse.taskcitationanalysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import org.njuse17advancedse.taskcitationanalysis.exception.PaperNotFoundProblem;
import org.njuse17advancedse.taskcitationanalysis.exception.ResearcherNotFoundProblem;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "论文" })
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
      List<String> res = service.getQuotedPapersByPaperId(id);
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      List<String> res = service.getQuotingPapersByPaperId(id);
      if (checkProblem(res)) return res;
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
      List<String> res = service.getPaperQuotedResearcher(id);
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      List<String> res = service.getPaperQuotingResearcher(id);
      if (checkProblem(res)) return res;
    }
    return null;
  }

  public PaperController(TaskCitationAnalysisService service) {
    this.service = service;
  }

  private boolean checkProblem(List<String> res) {
    if (res.size() != 3) return true;
    if (res.get(0).equals("Not Found")) {
      if (res.get(1).equals("Researcher")) throw new ResearcherNotFoundProblem(
        res.get(2)
      );
      if (res.get(1).equals("Paper")) throw new PaperNotFoundProblem(
        res.get(2)
      );
    }
    return true;
  }
}
