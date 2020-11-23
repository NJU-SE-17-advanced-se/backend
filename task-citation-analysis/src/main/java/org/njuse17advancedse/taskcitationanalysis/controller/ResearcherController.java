package org.njuse17advancedse.taskcitationanalysis.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Map;
import org.njuse17advancedse.taskcitationanalysis.service.TaskCitationAnalysisService;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@Api(tags = { "学者" })
@RequestMapping("/researchers")
@RestController
public class ResearcherController {
  private final TaskCitationAnalysisService service;

  // 某个学者引用了哪些学者
  // 某个学者被哪些学者引用
  @ApiOperation(
    value = "查看某学者的引用和被引情况",
    notes = "需求 7.2：研究者引用其他研究者及被其他研究者引用情况"
  )
  @GetMapping("/{id}/citations")
  public List<String> getResearcherCitations(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      List<String> res = service.getResearcherQuotedResearcher(id);
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      List<String> res = service.getResearcherQuotingResearcher(id);
      if (checkProblem(res)) return res;
    }
    return null;
  }

  // 某个学者的论文分别引用了哪些论文
  // 某个学者的论文分别被哪些论文引用
  @ApiOperation(
    value = "查看某学者的论文引用和被引情况",
    notes = "需求 7.2：研究者引用其他研究者的论文及论文被其他研究者引用情况"
  )
  @GetMapping("/{id}/citations/papers")
  public Map<String, List<String>> getResearcherPapersCitations(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      Map<String, List<String>> res = service.getQuotedPapersByResearcherId(id);
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      Map<String, List<String>> res = service.getQuotingPapersByResearcherId(
        id
      );
      if (checkProblem(res)) return res;
    }
    return null;
  }

  // 某个学者的论文分别引用了哪些学者
  // 某个学者的论文分别被哪些学者引用
  @ApiOperation(
    value = "查看某学者的论文引用其他学者和被其他学者引用情况",
    notes = "即，某个学者的论文分别引用了哪些学者，某个学者的论文分别被哪些学者引用"
  )
  @GetMapping("/{id}/citations/papers/researchers")
  public Map<String, List<String>> getResearcherPapersCitedResearchers(
    @ApiParam(value = "学者 id") @PathVariable String id,
    @ApiParam(value = "引用 quoting / 被引 quoted") @RequestParam String type
  ) {
    if (type.equals("quoted")) {
      Map<String, List<String>> res = service.getResearcherPaperQuotedResearcher(
        id
      );
      if (checkProblem(res)) return res;
    }
    if (type.equals("quoting")) {
      Map<String, List<String>> res = service.getResearcherPaperQuotingResearcher(
        id
      );
      if (checkProblem(res)) return res;
    }
    return null;
  }

  public ResearcherController(TaskCitationAnalysisService service) {
    this.service = service;
  }

  private boolean checkProblem(Map<String, List<String>> res) {
    if (res.containsKey("Not Found")) {
      List<String> parms = res.get("Not Found");
      throw Problem.valueOf(
        Status.INTERNAL_SERVER_ERROR,
        "Author Data Corrupted"
      );
    }
    return true;
  }

  private boolean checkProblem(List<String> res) {
    if (res.size() != 3) return true;
    if (res.get(0).equals("Not Found")) {
      throw Problem.valueOf(
        Status.INTERNAL_SERVER_ERROR,
        "Author Data Corrupted"
      );
    }
    return true;
  }
}
